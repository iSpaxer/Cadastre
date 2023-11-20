package TgBot.service.impl;

import TgBot.dto.ClientForOutputTelegramDTO;
import TgBot.dto.EditSendUsersDTO;
import TgBot.service.CallBackQueryHandlerService;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import TgBot.util.EmojiParserCustom;
import TgBot.util.cache.CacheCustom;
import TgBot.web.service.impl.ApiRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Slf4j
public class CallBackQueryHandlerServiceImpl implements CallBackQueryHandlerService {
    private TelegramBot telegramBot;
    private final ApiRequestService apiRequestService;
    private final EmojiParserCustom emojiParserCustom;
    private final CommonSendTextMessage commonSendTextMessage;
    private final CacheCustom cacheCustom;

    @Autowired
    public CallBackQueryHandlerServiceImpl(ApiRequestService apiRequestService, EmojiParserCustom emojiParserCustom, CommonSendTextMessage commonSendTextMessage, CacheCustom cacheCustom) {
        this.apiRequestService = apiRequestService;
        this.emojiParserCustom = emojiParserCustom;
        this.commonSendTextMessage = commonSendTextMessage;
        this.cacheCustom = cacheCustom;
    }

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void handle(Update update) {
        String data = update.getCallbackQuery().getData();
        if (data.substring(0, 22).equals("taken_client_with_tgId")) {
            takeClientWithTgId(update, data);
        }
    }

    private void takeClientWithTgId(Update update, String data) {
        String clientId = update.getCallbackQuery().getData().substring(24);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Long tgId = update.getCallbackQuery().getMessage().getChatId();
        ClientForOutputTelegramDTO client = null;
        try {
            client = apiRequestService.takeClientUsingTgbot(Integer.valueOf(clientId), tgId);
        } catch (Exception e) {
            commonSendTextMessage.sendTextMessage(
                    tgId,
                    emojiParserCustom.messageWithEmoji(
                            "Error take-up client :disappointed_relieved:\n"
                            + e.getMessage()
                    )
            );
        }

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(emojiParserCustom.messageWithEmoji(
                ":x: Клиент взят: " + client.getEngineerName() + ":construction_worker:"
                + "\n:id: " + client.getId()
                + "\n:bust_in_silhouette: " + client.getName()
                + "\n:telephone_receiver: +" + client.getPhone()
                + "\n:clock3: " + client.getCreatedData()
        ));

        // todo лучше доставать как JSON из CallBackQuery или кешировать
        EditSendUsersDTO dataCached = cacheCustom.cashe.get(clientId);
        for (int i = 0; i < dataCached.getSize(); i++) {
            editMessageText.setMessageId(dataCached.getMessageId().get(i));
            editMessageText.setChatId(dataCached.getEngTgId().get(i));
            try {
                cacheCustom.cashe.remove(clientId);
                telegramBot.execute(editMessageText);
            } catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());
            }
        }
    }


}
