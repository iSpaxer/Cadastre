package TgBot.web.service.impl;

import TgBot.dto.EngineerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;


@Service
public class ApiRequestService implements TgBot.web.service.ApiRequestService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ApiRequestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Boolean authenticationEngineer(EngineerDTO engineerDTO) {
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("/authenticationEngineer")
                    .bodyValue(engineerDTO)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
