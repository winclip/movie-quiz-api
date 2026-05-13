package dev.winclip.movie_quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "page_results")
public class PageResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(nullable = false)
	private int page;

	@Column(nullable = false)
	private short stars;

	@Column(name = "correct_count", nullable = false)
	private int correctCount;

	@Column(name = "total_count", nullable = false)
	private int totalCount;

	@Column(name = "completed_at", nullable = false)
	private Instant completedAt;

	public PageResult(Client client, int page, short stars, int correctCount, int totalCount) {
		this.client = client;
		this.page = page;
		this.stars = stars;
		this.correctCount = correctCount;
		this.totalCount = totalCount;
		this.completedAt = Instant.now();
	}
}
