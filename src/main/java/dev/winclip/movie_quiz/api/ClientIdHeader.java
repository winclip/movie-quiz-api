package dev.winclip.movie_quiz.api;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

public final class ClientIdHeader {

	public static final String NAME = "X-Client-Id";

	private ClientIdHeader() {
	}

	public static UUID parse(String value) {
		if (!StringUtils.hasText(value)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing header: " + NAME);
		}
		try {
			return UUID.fromString(value.trim());
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + NAME + ": " + value);
		}
	}
}
