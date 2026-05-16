package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.AnswerOptionResponse;
import dev.winclip.movie_quiz.api.dto.QuestionResponse;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.AnswerTranslation;
import dev.winclip.movie_quiz.entity.Question;
import dev.winclip.movie_quiz.entity.QuestionTranslation;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoMapper {

	public QuestionResponse toResponse(Question question) {
		return toResponse(question, "en");
	}

	public QuestionResponse toResponse(Question question, String locale) {
		var options = question.getAnswers().stream()
				.sorted(Comparator.comparing(Answer::getId))
				.map(a -> new AnswerOptionResponse(
						a.getId(),
						resolveAnswerText(a, locale),
						a.isCorrect()))
				.toList();
		return new QuestionResponse(
				question.getId(),
				resolveQuestionText(question, locale),
				question.getImageUrl(),
				question.getRevealedImageUrl(),
				options);
	}

	private static String resolveQuestionText(Question question, String locale) {
		List<QuestionTranslation> tr = question.getQuestionTranslations();
		if (tr != null && !tr.isEmpty()) {
			return tr.stream()
					.filter(t -> locale.equals(t.getLocale()))
					.map(QuestionTranslation::getTranslationText)
					.findFirst()
					.or(() -> tr.stream()
							.filter(t -> "en".equals(t.getLocale()))
							.map(QuestionTranslation::getTranslationText)
							.findFirst())
					.orElse(question.getQuestionText());
		}
		return question.getQuestionText();
	}

	private static String resolveAnswerText(Answer answer, String locale) {
		List<AnswerTranslation> tr = answer.getAnswerTranslations();
		if (tr != null && !tr.isEmpty()) {
			return tr.stream()
					.filter(t -> locale.equals(t.getLocale()))
					.map(AnswerTranslation::getTranslationText)
					.findFirst()
					.or(() -> tr.stream()
							.filter(t -> "en".equals(t.getLocale()))
							.map(AnswerTranslation::getTranslationText)
							.findFirst())
					.orElse(answer.getAnswerText());
		}
		return answer.getAnswerText();
	}
}
