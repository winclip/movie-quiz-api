package dev.winclip.movie_quiz.repository;

import dev.winclip.movie_quiz.entity.Client;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
