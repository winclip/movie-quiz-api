package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.AnswerOptionResponse;
import dev.winclip.movie_quiz.api.dto.QuestionResponse;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.Question;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoMapper {

	public QuestionResponse toResponse(Question question) {
		var options = question.getAnswers().stream()
				.sorted(Comparator.comparing(Answer::getId))
				.map(a -> new AnswerOptionResponse(a.getId(), a.getAnswerText(), a.isCorrect()))
				.toList();
		return new QuestionResponse(
				question.getId(),
				question.getQuestionText(),
				question.getImageUrl(),
				options);
	}
}
