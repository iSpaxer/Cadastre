package DBPostgres.service;

import DBPostgres.dto.client.ClientWithoutPhoneForTelegramDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiRequestService {
    private final WebClient.Builder webClientBuilder;

    public void sendNotificationInTelegram(ClientWithoutPhoneForTelegramDTO clientWithoutPhoneForTelegramDTO) throws Exception {
        String telegramInfoResponse = webClientBuilder.build()
                .post()
                .uri("notificOfNewClient")
                .bodyValue(clientWithoutPhoneForTelegramDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(telegramInfoResponse);
    }
}
