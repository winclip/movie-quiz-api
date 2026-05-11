package dev.winclip.movie_quiz.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateQuestionRequest(
		@NotBlank String text,
		String imageUrl,
		@NotEmpty @Size(min = 2, max = 20) @Valid List<CreateAnswerOptionRequest> options) {

	@AssertTrue(message = "Exactly one option must be marked correct")
	public boolean hasExactlyOneCorrect() {
		if (options == null) {
			return false;
		}
		return options.stream().filter(CreateAnswerOptionRequest::correct).count() == 1;
	}
}
