package TgBot.service;

import TgBot.telegramAPI.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerService {
    void handle(Update update);
}
