package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.api.dto.CreateAnswerOptionRequest;
import dev.winclip.movie_quiz.api.dto.CreateQuestionRequest;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.Question;
import dev.winclip.movie_quiz.repository.QuestionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Transactional(readOnly = true)
	public List<Question> findAllWithDetails() {
		return questionRepository.findAllWithDetails();
	}

	@Transactional
	public Question create(CreateQuestionRequest request) {
		var question = new Question();
		question.setQuestionText(request.text());
		question.setImageUrl(StringUtils.hasText(request.imageUrl()) ? request.imageUrl() : null);
		for (CreateAnswerOptionRequest opt : request.options()) {
			var answer = new Answer();
			answer.setQuestion(question);
			answer.setAnswerText(opt.text());
			answer.setCorrect(opt.correct());
			question.getAnswers().add(answer);
		}
		return questionRepository.save(question);
	}
}
