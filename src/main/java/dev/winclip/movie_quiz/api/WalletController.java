package dev.winclip.movie_quiz.api;

import dev.winclip.movie_quiz.api.dto.WalletResponse;
import dev.winclip.movie_quiz.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	@GetMapping
	public WalletResponse wallet(@RequestHeader(ClientIdHeader.NAME) String clientId) {
		return walletService.getWallet(ClientIdHeader.parse(clientId));
	}

	@PostMapping("/spend")
	public WalletResponse spend(@RequestHeader(ClientIdHeader.NAME) String clientId) {
		return walletService.spendCrystal(ClientIdHeader.parse(clientId));
	}

}
