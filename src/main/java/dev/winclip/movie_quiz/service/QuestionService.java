package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.api.dto.CreateAnswerOptionRequest;
import dev.winclip.movie_quiz.api.dto.CreateQuestionRequest;
import dev.winclip.movie_quiz.entity.Answer;
import dev.winclip.movie_quiz.entity.AnswerTranslation;
import dev.winclip.movie_quiz.entity.Question;
import dev.winclip.movie_quiz.entity.QuestionTranslation;
import dev.winclip.movie_quiz.i18n.SupportedLocales;
import dev.winclip.movie_quiz.repository.QuestionRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Transactional(readOnly = true)
	public List<Question> findAllWithDetails() {
		return questionRepository.findAllWithDetails();
	}

	@Transactional
	public Question create(CreateQuestionRequest request) {
		var question = new Question();
		question.setQuestionText(primaryText(request.texts()));
		question.setImageUrl(StringUtils.hasText(request.imageUrl()) ? request.imageUrl() : null);

		for (String locale : SupportedLocales.ALL) {
			var tr = new QuestionTranslation();
			tr.setQuestion(question);
			tr.setLocale(locale);
			tr.setTranslationText(request.texts().get(locale).trim());
			question.getQuestionTranslations().add(tr);
		}

		for (CreateAnswerOptionRequest opt : request.options()) {
			var answer = new Answer();
			answer.setQuestion(question);
			answer.setAnswerText(primaryText(opt.texts()));
			answer.setCorrect(opt.correct());
			for (String locale : SupportedLocales.ALL) {
				var tr = new AnswerTranslation();
				tr.setAnswer(answer);
				tr.setLocale(locale);
				tr.setTranslationText(opt.texts().get(locale).trim());
				answer.getAnswerTranslations().add(tr);
			}
			question.getAnswers().add(answer);
		}
		return questionRepository.save(question);
	}

	private static String primaryText(Map<String, String> texts) {
		String en = texts.get("en");
		if (StringUtils.hasText(en)) {
			return en.trim();
		}
		for (String locale : SupportedLocales.ALL) {
			String t = texts.get(locale);
			if (StringUtils.hasText(t)) {
				return t.trim();
			}
		}
		throw new IllegalStateException("No primary text");
	}
}
