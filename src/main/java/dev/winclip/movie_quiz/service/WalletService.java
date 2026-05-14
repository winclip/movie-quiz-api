package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.api.dto.WalletResponse;
import dev.winclip.movie_quiz.config.WalletProperties;
import dev.winclip.movie_quiz.entity.Client;
import dev.winclip.movie_quiz.quiz.WalletConstants;
import dev.winclip.movie_quiz.repository.ClientRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WalletService {

	private final ClientService clientService;
	private final ClientRepository clientRepository;
	private final WalletProperties walletProperties;

	public WalletService(
			ClientService clientService,
			ClientRepository clientRepository,
			WalletProperties walletProperties) {
		this.clientService = clientService;
		this.clientRepository = clientRepository;
		this.walletProperties = walletProperties;
	}

	@Transactional
	public WalletResponse getWallet(UUID clientId) {
		Client client = clientService.getOrCreate(clientId);
		Instant now = Instant.now();
		applyRegeneration(client, now);
		clientRepository.save(client);
		return toResponse(client, now);
	}

	@Transactional
	public WalletResponse spendCrystal(UUID clientId) {
		Client client = clientService.getOrCreate(clientId);
		Instant now = Instant.now();
		applyRegeneration(client, now);

		if (client.getCrystals() <= 0) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "No crystals available");
		}

		client.setCrystals((short) (client.getCrystals() - 1));
		if (client.getCrystals() < WalletConstants.MAX_CRYSTALS && client.getNextCrystalAt() == null) {
			client.setNextCrystalAt(now.plus(walletProperties.regenDuration()));
		}

		clientRepository.save(client);
		return toResponse(client, now);
	}

	private void applyRegeneration(Client client, Instant now) {
		while (client.getCrystals() < WalletConstants.MAX_CRYSTALS
				&& client.getNextCrystalAt() != null
				&& !now.isBefore(client.getNextCrystalAt())) {
			client.setCrystals((short) (client.getCrystals() + 1));
			if (client.getCrystals() >= WalletConstants.MAX_CRYSTALS) {
				client.setNextCrystalAt(null);
			} else {
				client.setNextCrystalAt(client.getNextCrystalAt().plus(walletProperties.regenDuration()));
			}
		}
	}

	private WalletResponse toResponse(Client client, Instant serverTime) {
		return new WalletResponse(
				client.getCrystals(),
				WalletConstants.MAX_CRYSTALS,
				walletProperties.regenSeconds(),
				client.getNextCrystalAt(),
				serverTime);
	}

}
