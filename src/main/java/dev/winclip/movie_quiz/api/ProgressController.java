package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.ProgressResponse;
import dev.winclip.movie_quiz.api.dto.ResetProgressResponse;
import dev.winclip.movie_quiz.service.PageProgressService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

	private final PageProgressService pageProgressService;

	public ProgressController(PageProgressService pageProgressService) {
		this.pageProgressService = pageProgressService;
	}

	@GetMapping
	public ProgressResponse progress(@RequestHeader(ClientIdHeader.NAME) String clientId) {
		return pageProgressService.getProgress(ClientIdHeader.parse(clientId));
	}

	@DeleteMapping
	public ResetProgressResponse reset(@RequestHeader(ClientIdHeader.NAME) String clientId) {
		long deletedLevels = pageProgressService.resetProgress(ClientIdHeader.parse(clientId));
		return new ResetProgressResponse(deletedLevels);
	}
}
