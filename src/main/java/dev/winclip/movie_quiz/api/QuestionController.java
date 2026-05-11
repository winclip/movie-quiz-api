package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.CreateQuestionRequest;
import dev.winclip.movie_quiz.api.dto.QuestionResponse;
import dev.winclip.movie_quiz.service.QuestionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<QuestionResponse> list() {
		return questionService.findAllWithDetails().stream()
				.map(questionDtoMapper::toResponse)
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionResponse create(@Valid @RequestBody CreateQuestionRequest body) {
		var saved = questionService.create(body);
		return questionDtoMapper.toResponse(saved);
	}
}
