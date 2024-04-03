package TgBot.service.impl;

import TgBot.dto.EngineerTelegramDTO;
import TgBot.dto.PricelistDTO;
import TgBot.dto.PricelistWithoutDateDTO;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import TgBot.web.service.impl.ApiRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class WebAppDataHandlerService implements TgBot.service.WebAppDataHandlerService {
    private TelegramBot telegramBot;
    private final CommonSendTextMessage commonSendTextMessage;
    private final ApiRequestService apiRequestService;
    private Gson gson;

    @Autowired
    public WebAppDataHandlerService(CommonSendTextMessage commonSendTextMessage, ApiRequestService apiRequestService) {
        this.commonSendTextMessage = commonSendTextMessage;
        this.apiRequestService = apiRequestService;
        gson = new Gson();
    }

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void handle(Update update) {
        WebAppData webAppData = update.getMessage().getWebAppData();
        String buttonText = webAppData.getButtonText();
//        setPrice(webAppData, update);
        if (buttonText.equals("Войти ❤")) {
            authenticationUsersThroughTelegramLogin(webAppData, update);
        }
        if (buttonText.equals("Set price! ✍\uFE0F")) {
            setPrice(webAppData, update);
        }

    }

    private void setPrice(WebAppData webAppData, Update update) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = webAppData.getData();

        try {
            System.err.println(json);
            List<PricelistDTO> pricelistWithoutDateDTOS = objectMapper.readValue(json, new TypeReference<List<PricelistDTO>>() {});
            String responseDB = apiRequestService.updatePricelist(update, pricelistWithoutDateDTOS);
            commonSendTextMessage.sendTextMessage(update, responseDB);
        } catch (JsonProcessingException e) {
            commonSendTextMessage.sendTextMessage(update, "❗ Ошибка распарсивания объекта! \uD83D\uDE35\u200D\uD83D\uDCAB\n"
                    + e.getMessage());
        }
    }

    private void authenticationUsersThroughTelegramLogin(WebAppData webAppData, Update update) {
        long chatId = update.getMessage().getChatId();
        EngineerTelegramDTO engineerTelegramDTO = gson.fromJson(webAppData.getData(), EngineerTelegramDTO.class);
        engineerTelegramDTO.setTgId(chatId);
        Boolean authenticate = apiRequestService.authenticationEngineer(engineerTelegramDTO, update);
        String message;
        if (authenticate) {
            message = "Вы успешно вошли в аккаунт!";
        } else {
            message = "Логин или пароль неверны";
        }
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),message);
        commonSendTextMessage.sendTextMessage(sendMessage);
    }

    private Boolean isActive(Update update) {
        return apiRequestService.telegramIsActive(update);
    }
}
