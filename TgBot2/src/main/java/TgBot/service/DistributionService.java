package TgBot.service;

import TgBot.telegramAPI.TelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface DistributionService {
    void init(TelegramBot telegramBot);
    void distribution(Update update);
}
