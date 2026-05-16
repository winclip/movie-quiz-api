package dev.winclip.movie_quiz.api.dto;

import dev.winclip.movie_quiz.i18n.SupportedLocales;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public record CreateQuestionRequest(
		@NotEmpty Map<String, String> texts,
		String imageUrl,
		String revealedImageUrl,
		@NotEmpty @Size(min = 2, max = 20) @Valid List<CreateAnswerOptionRequest> options) {

	@AssertTrue(message = "Question texts must include non-blank en, de, fr, pt, es")
	public boolean questionTextsComplete() {
		if (texts == null) {
			return false;
		}
		for (String locale : SupportedLocales.ALL) {
			if (!StringUtils.hasText(texts.get(locale))) {
				return false;
			}
		}
		return texts.keySet().equals(SupportedLocales.ALL_SET);
	}

	@AssertTrue(message = "Each option must have the same locales as the question with non-blank text")
	public boolean optionTextsMatchQuestion() {
		if (options == null || texts == null || !texts.keySet().equals(SupportedLocales.ALL_SET)) {
			return false;
		}
		for (CreateAnswerOptionRequest opt : options) {
			if (opt.texts() == null || !opt.texts().keySet().equals(SupportedLocales.ALL_SET)) {
				return false;
			}
			for (String locale : SupportedLocales.ALL) {
				if (!StringUtils.hasText(opt.texts().get(locale))) {
					return false;
				}
			}
		}
		return true;
	}

	@AssertTrue(message = "revealedImageUrl requires imageUrl")
	public boolean revealedImageRequiresBaseImage() {
		if (!StringUtils.hasText(revealedImageUrl)) {
			return true;
		}
		return StringUtils.hasText(imageUrl);
	}

	@AssertTrue(message = "Exactly one option must be marked correct")
	public boolean hasExactlyOneCorrect() {
		if (options == null) {
			return false;
		}
		return options.stream().filter(CreateAnswerOptionRequest::correct).count() == 1;
	}
}
