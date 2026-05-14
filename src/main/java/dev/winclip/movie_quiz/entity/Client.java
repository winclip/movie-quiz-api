package dev.winclip.movie_quiz.entity;

import dev.winclip.movie_quiz.quiz.WalletConstants;
import jakarta.persistence.Column;import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

	@Id
	private UUID id;

	@Column(nullable = false)
	private short crystals = WalletConstants.MAX_CRYSTALS;
	@Column(name = "next_crystal_at")
	private Instant nextCrystalAt;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	public Client(UUID id) {
		this.id = id;
		this.crystals = WalletConstants.MAX_CRYSTALS;
		this.createdAt = Instant.now();
	}}
