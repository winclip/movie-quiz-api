package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.Question;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("""
			SELECT q.id FROM Question q
			ORDER BY q.id
			""")
	List<Long> findPageIds(Pageable pageable);

	@Query("""
			SELECT DISTINCT q FROM Question q
			JOIN FETCH q.answers a
			WHERE q.id IN :ids
			""")
	List<Question> findByIdInWithDetails(@Param("ids") List<Long> ids);
}
