package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.entity.Question;
import dev.winclip.movie_quiz.repository.QuestionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
