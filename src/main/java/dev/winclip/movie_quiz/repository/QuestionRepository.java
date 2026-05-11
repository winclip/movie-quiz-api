package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("""
			SELECT DISTINCT q FROM Question q
			JOIN FETCH q.category
			JOIN FETCH q.answers
			""")
	List<Question> findAllWithDetails();

	@Query("""
			SELECT DISTINCT q FROM Question q
			JOIN FETCH q.category
			JOIN FETCH q.answers
			WHERE q.category.id = :categoryId
			""")
	List<Question> findAllByCategoryIdWithDetails(@Param("categoryId") Long categoryId);
}
