package dev.winclip.movie_quiz.api.dto;

public record SubmitPageResponse(
		int page,
		int stars,
		int correctCount,
		int totalCount,
		int bestStars) {
}
