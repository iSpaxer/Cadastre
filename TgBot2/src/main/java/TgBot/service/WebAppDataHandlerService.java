package TgBot.service;

import TgBot.telegramAPI.TelegramBot;
import TgBot.util.InitTelegramBot;
import org.springframework.stereotype.Service;

@Service
public interface WebAppDataHandlerService extends HandlerService, InitTelegramBot {
    void init(TelegramBot telegramBot);
}
