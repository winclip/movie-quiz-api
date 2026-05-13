package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SubmitPageRequest(@NotEmpty @Valid List<SubmitAnswerRequest> answers) {
}
