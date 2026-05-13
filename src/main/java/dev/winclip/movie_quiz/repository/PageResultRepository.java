package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.PageResult;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageResultRepository extends JpaRepository<PageResult, Long> {

	List<PageResult> findByClient_IdOrderByPageAsc(UUID clientId);

	Optional<PageResult> findByClient_IdAndPage(UUID clientId, int page);
}
