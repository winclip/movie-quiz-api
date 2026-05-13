package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Map;

public record CreateAnswerOptionRequest(@NotEmpty Map<String, String> texts, boolean correct) {
}
