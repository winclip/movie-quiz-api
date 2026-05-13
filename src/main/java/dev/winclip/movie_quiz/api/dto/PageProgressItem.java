package dev.winclip.movie_quiz.api.dto;

import java.time.Instant;

public record PageProgressItem(
		int page,
		int stars,
		int correctCount,
		int totalCount,
		Instant completedAt) {
}
