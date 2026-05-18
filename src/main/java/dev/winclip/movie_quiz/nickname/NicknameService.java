package dev.winclip.movie_quiz.nickname;

import dev.winclip.movie_quiz.repository.ClientRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class NicknameService {

	private static final int SUFFIX_LENGTH = 8;

	private final ClientRepository clientRepository;

	public NicknameService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public String generateUniqueNickname(UUID clientId) {
		String suffix = suffixFromClientId(clientId);
		ArrayList<String> prefixes = new ArrayList<>(NicknamePrefixes.ALL);
		Collections.shuffle(prefixes);

		for (String prefix : prefixes) {
			String nickname = prefix + "_" + suffix;
			if (!clientRepository.existsByNickname(nickname)) {
				return nickname;
			}
		}

		for (int extra = 2; extra < 1000; extra++) {
			String nickname = NicknamePrefixes.ALL.get(extra % NicknamePrefixes.ALL.size())
					+ "_" + suffix + "_" + extra;
			if (!clientRepository.existsByNickname(nickname)) {
				return nickname;
			}
		}

		throw new IllegalStateException("Unable to allocate unique nickname for client " + clientId);
	}

	static String suffixFromClientId(UUID clientId) {
		return clientId.toString().replace("-", "").substring(0, SUFFIX_LENGTH).toLowerCase();
	}
}
