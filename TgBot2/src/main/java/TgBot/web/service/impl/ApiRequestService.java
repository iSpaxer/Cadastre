package TgBot.web.service.impl;

import TgBot.dto.ClientForOutputTelegramDTO;
import TgBot.dto.ClientTakeTelegramDTO;
import TgBot.dto.EngineerTelegramDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ApiRequestService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ApiRequestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Boolean authenticationEngineer(EngineerTelegramDTO engineerTelegramDTO) {
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("/authenticationEngineerTelegram")
                    .bodyValue(engineerTelegramDTO)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Boolean telegramIsActive(Update update) {
        Long chatId = update.getMessage().getChatId();
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("/telegramIsActive")
                    .bodyValue(chatId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public ClientForOutputTelegramDTO takeClientUsingTgbot(Integer clientId, Long tgId) {

        ClientTakeTelegramDTO clientTakeTelegramDTO = new ClientTakeTelegramDTO();
        clientTakeTelegramDTO.setClientId(clientId);
        clientTakeTelegramDTO.setTgId(tgId);
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("/takeClientUsingTgbot")
                    .bodyValue(clientTakeTelegramDTO)
                    .retrieve()
                    .bodyToMono(ClientForOutputTelegramDTO.class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Long[] getAllEngineersWithTgId() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/getAllEngineersWithTgId")
                    .retrieve()
                    .bodyToMono(Long[].class)
                    .block();
        } catch (WebClientResponseException webClientResponseException) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

//    public EngineerDTO getEngineerByTgId (Update update) {
//        long tgId = update.getMessage().getChatId();
//        try {
//            return webClientBuilder.build()
//                    .get()
//                    .uri("/getEngineerByTgId")
//                    .retrieve()
//                    .bodyToMono(EngineerDTO.class)
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
////            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (Exception e) {
////            throw new UnknownException(e.getMessage(), new Date());
//        }
//    }
//
//    public Page<ClientDbDTO> getAllClients(String page, String size) {
//        return webClientBuilder.build()
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/getClients")
//                        .queryParamIfPresent("page", Optional.ofNullable(page))
//                        .queryParamIfPresent("size", Optional.ofNullable(size))
//                        .build())
//                .retrieve()
//                .bodyToMono(RestPage.class)
//                .block();
//    }
//
//    public void takeClient(EngineerDTO engineerDTO, ClientDbDTO clientDbDTO) {
//        EngineerDTO requestEngineer = new EngineerDTO(engineerDTO.getLogin(), null);
//        ClientDbDTO requestClient = new ClientDbDTO(clientDbDTO.getId(), null, null, null, null);
//        if (requestEngineer.getLogin() == null || requestClient.getId() == null) {
//            throw new DBRequestException("Login engineer or id client is empty!", HttpStatus.BAD_REQUEST.value());
//        }
//        EngineerAndClient engineerAndClient = new EngineerAndClient(requestEngineer, requestClient);
//        try {
//            ///TODO неправильная обработка возвращаемого значения
//            webClientBuilder.build()
//                    .post()
//                    .uri("/takeClient")
//                    .bodyValue(engineerAndClient)
//                    .retrieve()
//                    .bodyToMono(HttpStatus.class)   ///TODO в теле ничего нет
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
//            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (Exception e) {
//            throw new UnknownException(e.getMessage(), new Date());
//        }
//    }
//
//    public String updatePasswordByEngineer(EngineerUpdatePasswordDTO engineerUpdatePasswordDTO, HttpServletRequest request) {
//        String messageFromDB;
//        var auth = (Authentication) request.getUserPrincipal();
//        var engDetails = (EngDetails) auth.getPrincipal();
//        // получаем аутиф инжерена и (для ДБ) прокидываем логин объекту с новым паролем
//        EngineerDTO engineerAuthDTO = getAuthEngineer(request);
//        engineerUpdatePasswordDTO.setLogin(engineerAuthDTO.getLogin());
//        try {
//            ///TODO неправильная обработка возвращаемого значения
//            messageFromDB = webClientBuilder.build()
//                    .post()
//                    .uri("/updateEngineerPassword")
//                    .bodyValue(engineerUpdatePasswordDTO)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
//            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (EngineerNotAuthentication e) {
//            throw e;
//        } catch (Exception e) {
//            throw new UnknownException(e.getMessage(), new Date());
//        }
//        // назначаем новый пароль UserDetails в spring security
//        engineerAuthDTO.setPassword(engineerUpdatePasswordDTO.getNewPassword());
//
//        return messageFromDB;
//    }
//
//
//    public List<PricelistDTO> getPricelist() {
//        try {
//            return webClientBuilder.build()
//                    .get()
//                    .uri("/getPricelist")
//                    .retrieve()
//                    .bodyToFlux(PricelistDTO.class)
//                    .collectList()
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
//            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (Exception e) {
//            throw new UnknownException(e.getMessage(), new Date());
//        }
//    }
//
//    public String pricelistSwap() {
//        try {
//            return webClientBuilder.build()
//                    .get()
//                    .uri("/pricelistSwap")
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
//            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (Exception e) {
//            throw new UnknownException(e.getMessage(), new Date());
//        }
//    }
//
//    public String updatePricelist(List<PricelistDTO> pricelistDTOList) {
//        String messageFromeDB;
//        try {
//            ///TODO неправильная обработка возвращаемого значения
//            messageFromeDB = webClientBuilder.build()
//                    .post()
//                    .uri("/updatePricelist")
//                    .bodyValue(pricelistDTOList)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        } catch (WebClientResponseException webClientResponseException) {
//            throw new DBRequestException(webClientResponseException.getMessage(), webClientResponseException.getStatusCode().value());
//        } catch (Exception e) {
//            throw new UnknownException(e.getMessage(), new Date());
//        }
//        return messageFromeDB;
//    }

//}
