package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
