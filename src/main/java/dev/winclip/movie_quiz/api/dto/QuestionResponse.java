package dev.winclip.movie_quiz.api.dto;

import java.util.List;

public record QuestionResponse(
		Long id,
		String text,
		String imageUrl,
		String revealedImageUrl,
		List<AnswerOptionResponse> options) {
}
