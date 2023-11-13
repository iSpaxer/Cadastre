package TgBot.service.impl;

import TgBot.dto.EngineerDTO;
import TgBot.dto.EngineerWithUsernameDTO;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import TgBot.web.service.impl.ApiRequestService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;

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
        // TODO распарсить JSON в объект EngineerDTO
        EngineerWithUsernameDTO engineerWithUsernameDTO = gson.fromJson(webAppData.getData(), EngineerWithUsernameDTO.class);
        Boolean authenticate = apiRequestService.authenticationEngineer(new EngineerDTO(engineerWithUsernameDTO.getUsername(),
                engineerWithUsernameDTO.getPassword()));
        String message;
        if (authenticate) {
            message = "Вы успешно вошли в аккаунт!";
        } else {
            message = "Логин или пароль неверны";
        }
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),message);
        commonSendTextMessage.sendTextMessage(sendMessage);
    }
}
