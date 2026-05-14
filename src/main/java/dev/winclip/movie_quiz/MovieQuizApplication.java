package dev.winclip.movie_quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import dev.winclip.movie_quiz.config.WalletProperties;

@SpringBootApplication
@EnableConfigurationProperties(WalletProperties.class)
public class MovieQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieQuizApplication.class, args);
	}

}
