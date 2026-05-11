package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.AnswerOptionResponse;
import dev.winclip.movie_quiz.api.dto.CategoryResponse;
import dev.winclip.movie_quiz.api.dto.QuestionResponse;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.Question;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoMapper {

	public QuestionResponse toResponse(Question question) {
		var category = question.getCategory();
		var options = question.getAnswers().stream()
				.sorted(Comparator.comparing(Answer::getId))
				.map(a -> new AnswerOptionResponse(a.getId(), a.getAnswerText()))
				.toList();
		return new QuestionResponse(
				question.getId(),
				question.getQuestionText(),
				question.getImageUrl(),
				new CategoryResponse(category.getId(), category.getName()),
				options);
	}
}
