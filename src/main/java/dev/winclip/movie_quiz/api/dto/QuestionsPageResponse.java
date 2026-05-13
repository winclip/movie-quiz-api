package dev.winclip.movie_quiz.api.dto;

import java.util.List;

public record QuestionsPageResponse(
		List<QuestionResponse> items,
		int page,
		int size,
		long totalItems,
		int totalPages,
		boolean hasNext) {
}

