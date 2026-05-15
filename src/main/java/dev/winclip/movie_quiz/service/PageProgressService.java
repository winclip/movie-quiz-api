package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.api.dto.PageProgressItem;
import dev.winclip.movie_quiz.api.dto.ProgressResponse;
import dev.winclip.movie_quiz.api.dto.SubmitAnswerRequest;
import dev.winclip.movie_quiz.api.dto.SubmitPageRequest;
import dev.winclip.movie_quiz.api.dto.SubmitPageResponse;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.PageResult;
import dev.winclip.movie_quiz.entity.Question;
import dev.winclip.movie_quiz.quiz.QuizConstants;
import dev.winclip.movie_quiz.quiz.StarRating;
import dev.winclip.movie_quiz.repository.PageResultRepository;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PageProgressService {

	private final ClientService clientService;
	private final QuestionService questionService;
	private final PageResultRepository pageResultRepository;

	public PageProgressService(
			ClientService clientService,
			QuestionService questionService,
			PageResultRepository pageResultRepository) {
		this.clientService = clientService;
		this.questionService = questionService;
		this.pageResultRepository = pageResultRepository;
	}

	@Transactional
	public SubmitPageResponse submit(UUID clientId, int page, SubmitPageRequest request) {
		if (page < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page: " + page);
		}

		var questions = questionService.findPageWithDetails(page, QuizConstants.PAGE_SIZE);
		if (questions.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found: " + page);
		}

		validateAnswers(questions, request.answers());

		int correctCount = countCorrect(questions, request.answers());
		int totalCount = questions.size();
		int stars = StarRating.stars(correctCount, totalCount);

		var client = clientService.getOrCreate(clientId);
		int bestStars = pageResultRepository.findByClient_IdAndPage(clientId, page)
				.map(existing -> {
					int updatedBest = Math.max(existing.getStars(), stars);
					existing.setStars((short) updatedBest);
					existing.setCorrectCount(correctCount);
					existing.setTotalCount(totalCount);
					existing.setCompletedAt(Instant.now());
					pageResultRepository.save(existing);
					return updatedBest;
				})
				.orElseGet(() -> {
					pageResultRepository.save(new PageResult(client, page, (short) stars, correctCount, totalCount));
					return stars;
				});

		return new SubmitPageResponse(page, stars, correctCount, totalCount, bestStars);
	}

	@Transactional
	public long resetProgress(UUID clientId) {
		return pageResultRepository.deleteByClient_Id(clientId);
	}

	@Transactional(readOnly = true)
	public ProgressResponse getProgress(UUID clientId) {
		long totalItems = questionService.countQuestions();
		int totalPages = totalItems == 0
				? 0
				: (int) ((totalItems + QuizConstants.PAGE_SIZE - 1) / QuizConstants.PAGE_SIZE);

		var pages = pageResultRepository.findByClient_IdOrderByPageAsc(clientId).stream()
				.map(r -> new PageProgressItem(
						r.getPage(),
						r.getStars(),
						r.getCorrectCount(),
						r.getTotalCount(),
						r.getCompletedAt()))
				.toList();

		return new ProgressResponse(totalPages, QuizConstants.PAGE_SIZE, pages);
	}

	private static void validateAnswers(List<Question> questions, List<SubmitAnswerRequest> answers) {
		if (answers.size() != questions.size()) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Expected " + questions.size() + " answers, got " + answers.size());
		}

		Map<Long, Question> questionsById = questions.stream()
				.collect(Collectors.toMap(Question::getId, Function.identity()));

		Set<Long> seenQuestionIds = new HashSet<>();
		for (SubmitAnswerRequest answer : answers) {
			if (!seenQuestionIds.add(answer.questionId())) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Duplicate answer for question: " + answer.questionId());
			}
			var question = questionsById.get(answer.questionId());
			if (question == null) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Question does not belong to page: " + answer.questionId());
			}
			boolean answerBelongsToQuestion = question.getAnswers().stream()
					.anyMatch(a -> a.getId().equals(answer.answerId()));
			if (!answerBelongsToQuestion) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST,
						"Answer does not belong to question: " + answer.answerId());
			}
		}
	}

	private static int countCorrect(List<Question> questions, List<SubmitAnswerRequest> answers) {
		Map<Long, Long> chosenAnswerByQuestion = answers.stream()
				.collect(Collectors.toMap(SubmitAnswerRequest::questionId, SubmitAnswerRequest::answerId));

		int correct = 0;
		for (Question question : questions) {
			Long chosenAnswerId = chosenAnswerByQuestion.get(question.getId());
			boolean isCorrect = question.getAnswers().stream()
					.filter(Answer::isCorrect)
					.anyMatch(a -> a.getId().equals(chosenAnswerId));
			if (isCorrect) {
				correct++;
			}
		}
		return correct;
	}
}
