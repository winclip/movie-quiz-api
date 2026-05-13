package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.SubmitPageRequest;
import dev.winclip.movie_quiz.api.dto.SubmitPageResponse;
import dev.winclip.movie_quiz.service.PageProgressService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pages")
public class PageProgressController {

	private final PageProgressService pageProgressService;

	public PageProgressController(PageProgressService pageProgressService) {
		this.pageProgressService = pageProgressService;
	}

	@PostMapping("/{page}/submit")
	public SubmitPageResponse submit(
			@PathVariable int page,
			@RequestHeader(ClientIdHeader.NAME) String clientId,
			@Valid @RequestBody SubmitPageRequest body) {
		return pageProgressService.submit(ClientIdHeader.parse(clientId), page, body);
	}
}
