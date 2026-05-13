package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.constraints.NotNull;

public record SubmitAnswerRequest(
		@NotNull Long questionId,
		@NotNull Long answerId) {
}
