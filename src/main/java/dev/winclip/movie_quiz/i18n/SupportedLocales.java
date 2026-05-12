package dev.winclip.movie_quiz.i18n;

import java.util.List;
import java.util.Set;

public final class SupportedLocales {

	private SupportedLocales() {
	}

	public static final List<String> ALL = List.of("en", "de", "fr", "pt", "es");

	public static final Set<String> ALL_SET = Set.copyOf(ALL);
}
