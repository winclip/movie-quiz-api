package dev.winclip.movie_quiz.service;

import dev.winclip.movie_quiz.entity.Client;
import dev.winclip.movie_quiz.repository.ClientRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

	private final ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Transactional
	public Client getOrCreate(UUID clientId) {
		return clientRepository.findById(clientId)
				.orElseGet(() -> clientRepository.save(new Client(clientId)));
	}
}
