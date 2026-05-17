package dev.winclip.movie_quiz.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "text", nullable = false, columnDefinition = "TEXT")
	private String questionText;

	@Column(name = "image_url", columnDefinition = "TEXT")
	private String imageUrl;

	@Column(name = "revealed_image_url", columnDefinition = "TEXT")
	private String revealedImageUrl;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@BatchSize(size = 32)
	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionTranslation> questionTranslations = new ArrayList<>();
}
