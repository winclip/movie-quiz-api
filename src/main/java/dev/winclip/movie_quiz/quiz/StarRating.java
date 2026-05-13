package dev.winclip.movie_quiz.quiz;

public final class StarRating {

	private StarRating() {
	}

	public static int stars(int correctCount, int totalCount) {
		if (correctCount == 0) {
			return 0;
		}
		if (correctCount == totalCount) {
			return 3;
		}
		if (correctCount == totalCount - 1) {
			return 2;
		}
		return 1;
	}
}
