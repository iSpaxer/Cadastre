package Boot.cadastreCompany.service;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApiRequestService {
    private WebClient.Builder webClientBuilder;

    @Autowired
    public ApiRequestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<ClientDTO> getAllClients() {
        return webClientBuilder.build()
                .get()
                .uri("/allClients")
                .retrieve()
                .bodyToFlux(ClientDTO.class)
                .collectList()
                .block();
    }

    public ClientDTO getLastClient() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/lastClient9")
                    .retrieve()
                    .bodyToMono(ClientDTO.class)
                    .block();

        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

    public Optional<EngineerDTO> findByEngineer(String login) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8088/DB/findByEngineer")
                    .retrieve()
                    .bodyToMono(Optional.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }

    }

    /// TODO post save client
    public void saveClient(ClientDTO clientDTO) {
        try {
            ///TODO неправильная обработка возвращаемого значения
            webClientBuilder.build()
                    .post()
                    .uri("/saveClient")
                    .bodyValue(clientDTO)
                    .retrieve()
                    .bodyToMono(HttpStatus.class)   ///TODO в теле ничего нет
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

}
