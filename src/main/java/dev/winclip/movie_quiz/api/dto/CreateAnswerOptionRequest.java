package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAnswerOptionRequest(@NotBlank String text, boolean correct) {
}
