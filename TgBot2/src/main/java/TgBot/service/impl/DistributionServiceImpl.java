package TgBot.service.impl;

import TgBot.service.CallBackQueryHandlerService;
import TgBot.service.DistributionService;
import TgBot.service.MessageHandlerService;
import TgBot.service.WebAppDataHandlerService;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class DistributionServiceImpl implements DistributionService {
    private TelegramBot telegramBot;
    private final CommonSendTextMessage commonSendTextMessage;
    private final MessageHandlerService messageHandlerService;
    private final WebAppDataHandlerService webAppDataHandlerService;
    private final CallBackQueryHandlerService callBackQueryHandlerService;



    @Autowired
    public DistributionServiceImpl(MessageHandlerService messageHandlerService, WebAppDataHandlerService webAppDataHandlerService, CommonSendTextMessage commonSendTextMessage, CommonSendTextMessage commonSendTextMessage1, CallBackQueryHandlerService callBackQueryHandlerService) {
        this.messageHandlerService = messageHandlerService;
        this.webAppDataHandlerService = webAppDataHandlerService;
        this.commonSendTextMessage = commonSendTextMessage;
        this.callBackQueryHandlerService = callBackQueryHandlerService;
    }

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        messageHandlerService.init(telegramBot);
        webAppDataHandlerService.init(telegramBot);
        commonSendTextMessage.init(telegramBot);
        callBackQueryHandlerService.init(telegramBot);
    }

    @Override
    public void distribution(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandlerService.handle(update);
        }

        if (update.hasMessage() && Optional.ofNullable(update.getMessage().getWebAppData()).isPresent()) {
            webAppDataHandlerService.handle(update);
        }
        if (update.hasCallbackQuery()) {
            callBackQueryHandlerService.handle(update);
        }
    }
}
