package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("""
			SELECT DISTINCT q FROM Question q
			JOIN FETCH q.answers a
			""")
	List<Question> findAllWithDetails();
}
