package TgBot.service;

import TgBot.telegramAPI.TelegramBot;
import TgBot.util.InitTelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface MessageHandlerService extends HandlerService, InitTelegramBot {
    void init(TelegramBot telegramBot);
}
