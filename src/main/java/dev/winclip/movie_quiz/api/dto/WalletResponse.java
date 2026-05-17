package dev.winclip.movie_quiz.api.dto;

import java.time.Instant;

public record WalletResponse(
		int crystals,
		int regenCap,
		int maxCrystals,
		long regenSeconds,
		Instant nextCrystalAt,
		Instant serverTime,
		int granted) {
}
