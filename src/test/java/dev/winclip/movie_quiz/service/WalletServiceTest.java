package dev.winclip.movie_quiz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.winclip.movie_quiz.api.dto.WalletResponse;
import dev.winclip.movie_quiz.config.WalletProperties;
import dev.winclip.movie_quiz.entity.Client;
import dev.winclip.movie_quiz.quiz.WalletConstants;
import dev.winclip.movie_quiz.repository.ClientRepository;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

	private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

	@Mock
	private ClientService clientService;

	@Mock
	private ClientRepository clientRepository;

	private WalletService walletService;

	@BeforeEach
	void setUp() {
		walletService = new WalletService(
				clientService, clientRepository, new WalletProperties(1200));
	}

	@Test
	void rewardAtThreeCrystalsGrantsOne() {
		Client client = clientWithCrystals((short) 3);
		when(clientService.getOrCreate(CLIENT_ID)).thenReturn(client);

		WalletResponse response = walletService.rewardCrystal(CLIENT_ID, 1);

		assertEquals(4, response.crystals());
		assertEquals(10, response.maxCrystals());
		assertEquals(1, response.granted());
		assertNull(client.getNextCrystalAt());
		verify(clientRepository).save(any(Client.class));
	}

	private static Client clientWithCrystals(short crystals) {
		Client client = new Client(CLIENT_ID);
		client.setCrystals(crystals);
		return client;
	}
}
