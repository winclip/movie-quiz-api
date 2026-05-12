package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.CreateQuestionRequest;
import dev.winclip.movie_quiz.api.dto.CreatedQuestionResponse;
import dev.winclip.movie_quiz.api.dto.QuestionResponse;
import dev.winclip.movie_quiz.i18n.SupportedLocales;
import dev.winclip.movie_quiz.service.QuestionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<QuestionResponse> list(@RequestParam(defaultValue = "en") String locale) {
		if (!SupportedLocales.ALL_SET.contains(locale)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported locale: " + locale);
		}
		return questionService.findAllWithDetails().stream()
				.map(q -> questionDtoMapper.toResponse(q, locale))
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CreatedQuestionResponse create(@Valid @RequestBody CreateQuestionRequest body) {
		var saved = questionService.create(body);
		return new CreatedQuestionResponse(saved.getId());
	}
}
