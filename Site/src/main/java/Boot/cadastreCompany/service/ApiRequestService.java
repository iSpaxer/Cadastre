package Boot.cadastreCompany.service;

import Boot.cadastreCompany.dto.*;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.EngineerNotAuthentication;
import Boot.cadastreCompany.exception.UnknownException;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.utils.RestPage;
import Boot.cadastreCompany.utils.wrapper.EngineerAndClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ApiRequestService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ApiRequestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Page<ClientDbDTO> getAllClients(String page, String size) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getClients")
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .queryParamIfPresent("size", Optional.ofNullable(size))
                        .build())
                .retrieve()
                .bodyToMono(RestPage.class)
                .block();
    }


    public Page<ClientDbDTO> getActiveClients(String page, String size) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getActiveClients")
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .queryParamIfPresent("size", Optional.ofNullable(size))
                        .build())
                .retrieve()
                .bodyToMono(RestPage.class)
                .block();
    }

    public Page<ClientDbDTO> getEndedClients(String page, String size) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getEndedClients")
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .queryParamIfPresent("size", Optional.ofNullable(size))
                        .build())
                .retrieve()
                .bodyToMono(RestPage.class)
                .block();
    }

    private void checkingEmptyString(String... values) {
        for (String e : values) {
            if (e == null) {
                e = "";
            }
        }
    }

    private String createGetRequest(String s, List<String> list) {
        s += "?";
        for (String element : list) {
            if (element.isBlank())
                s += element + "&";
        }
        return s;
    }

    public ClientDbDTO getLastClient() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/lastClient")
                    .retrieve()
                    .bodyToMono(ClientDbDTO.class)
                    .block();

        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

    // for checking login and password. From DB returned EngineerDTO
    public EngineerDTO findByEngineer(String login) {
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("/findByEngineer")
                    .bodyValue(login)
                    .retrieve()
                    .bodyToMono(EngineerDTO.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
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

    public void takeClient(EngineerDTO engineerDTO, ClientDbDTO clientDbDTO) {
        EngineerDTO requestEngineer = new EngineerDTO(engineerDTO.getLogin(), null);
        ClientDbDTO requestClient = new ClientDbDTO(clientDbDTO.getId(), null, null, null, null);
        if (requestEngineer.getLogin() == null || requestClient.getId() == null) {
            throw new DBRequestException("Login engineer or id client is empty!", HttpStatus.BAD_REQUEST.value());
        }
        EngineerAndClient engineerAndClient = new EngineerAndClient(requestEngineer, requestClient);
        try {
            ///TODO неправильная обработка возвращаемого значения
            webClientBuilder.build()
                    .post()
                    .uri("/takeClient")
                    .bodyValue(engineerAndClient)
                    .retrieve()
                    .bodyToMono(HttpStatus.class)   ///TODO в теле ничего нет
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

    public String updatePasswordByEngineer(EngineerUpdatePasswordDTO engineerUpdatePasswordDTO, HttpServletRequest request) {
        String messageFromDB;
        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        // получаем аутиф инжерена и (для ДБ) прокидываем логин объекту с новым паролем
        EngineerDTO engineerAuthDTO = getAuthEngineer(request);
        engineerUpdatePasswordDTO.setLogin(engineerAuthDTO.getLogin());
        try {
            ///TODO неправильная обработка возвращаемого значения
            messageFromDB = webClientBuilder.build()
                    .post()
                    .uri("/updateEngineerPassword")
                    .bodyValue(engineerUpdatePasswordDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (EngineerNotAuthentication e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
        // назначаем новый пароль UserDetails в spring security
        engineerAuthDTO.setPassword(engineerUpdatePasswordDTO.getNewPassword());

        return messageFromDB;
    }

    private EngineerDTO getAuthEngineer (HttpServletRequest request) {
        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        var engDTO = engDetails.getEngineerDTO();
        return engDTO;
    }

    public List<PricelistDTO> getPricelist() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/getPricelist")
                    .retrieve()
                    .bodyToFlux(PricelistDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

    public String pricelistSwap() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/pricelistSwap")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
    }

    public String updateDeadline(PricelistDTO pricelistDTO) {
        String messageFromeDB;
        try {
            ///TODO неправильная обработка возвращаемого значения
            messageFromeDB = webClientBuilder.build()
                    .post()
                    .uri("/updatePricelistDeadline")
                    .bodyValue(pricelistDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
        return messageFromeDB;
    }

    public String updateCost(PricelistDTO pricelistDTO) {
        String messageFromeDB;
        try {
            ///TODO неправильная обработка возвращаемого значения
            messageFromeDB = webClientBuilder.build()
                    .post()
                    .uri("/updatePricelistCost")
                    .bodyValue(pricelistDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
        return messageFromeDB;
    }

    public String updatePricelist(List<PricelistDTO> pricelistDTOList) {
        String messageFromeDB;
        try {
            ///TODO неправильная обработка возвращаемого значения
            messageFromeDB = webClientBuilder.build()
                    .post()
                    .uri("/updatePricelist")
                    .bodyValue(pricelistDTOList)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), new Date());
        }
        return messageFromeDB;
    }

}
