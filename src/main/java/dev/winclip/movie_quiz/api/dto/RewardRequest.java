package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RewardRequest(@Min(1) @Max(2) int amount) {
}
