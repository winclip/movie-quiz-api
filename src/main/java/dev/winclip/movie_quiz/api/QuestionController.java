package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.CreateQuestionRequest;
import dev.winclip.movie_quiz.api.dto.CreatedQuestionResponse;
import dev.winclip.movie_quiz.api.dto.QuestionsPageResponse;
import dev.winclip.movie_quiz.i18n.SupportedLocales;
import dev.winclip.movie_quiz.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
	private final QuestionService questionService;
	private final QuestionDtoMapper questionDtoMapper;

	public QuestionController(QuestionService questionService, QuestionDtoMapper questionDtoMapper) {
		this.questionService = questionService;
		this.questionDtoMapper = questionDtoMapper;
	}

	@GetMapping
	public QuestionsPageResponse list(
			@RequestParam(defaultValue = "en") String locale,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		if (!SupportedLocales.ALL_SET.contains(locale)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported locale: " + locale);
		}
		if (page < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page: " + page);
		}
		if (size < 1 || size > 50) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size: " + size);
		}

		var items = questionService.findPageWithDetails(page, size).stream()
				.map(q -> questionDtoMapper.toResponse(q, locale))
				.toList();
		long totalItems = questionService.countQuestions();
		int totalPages = (int) ((totalItems + size - 1) / size);
		boolean hasNext = page + 1 < totalPages;
		return new QuestionsPageResponse(items, page, size, totalItems, totalPages, hasNext);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CreatedQuestionResponse create(@Valid @RequestBody CreateQuestionRequest body) {
		var saved = questionService.create(body);
		return new CreatedQuestionResponse(saved.getId());
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @Valid @RequestBody CreateQuestionRequest body) {
		questionService.update(id, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		questionService.deleteById(id);
	}
}
