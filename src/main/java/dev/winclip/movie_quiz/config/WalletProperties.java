package dev.winclip.movie_quiz.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wallet")
public record WalletProperties(int regenSeconds) {

	public WalletProperties {
		if (regenSeconds < 1) {
			throw new IllegalArgumentException("wallet.regen-seconds must be >= 1");
		}
	}

	public Duration regenDuration() {
		return Duration.ofSeconds(regenSeconds);
	}

}
