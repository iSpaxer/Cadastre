package ru.tg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tg.service.UpdateProducer;
import ru.tg.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static rabitmq.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private MessageUtils messageUtils;
    private UpdateProducer updateProducer;

    @Autowired
    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    // круговое соединение с классом тгБота
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    // сортировка (валидация) полученных данных
    public void validationReceivedData(Update update) {
        if (update == null) {
            log.error("Received data is null");
        }

        if (update.getMessage() != null) {
            sortingReceivedData(update);
        }
    }

    // сортировка полученноо сообщения
    private void sortingReceivedData(Update update) {
        Message message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);

        } else if (message.getDocument() != null) {
            processDocMessage(update);

        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
             setUnsupportedMessageView(update);
        }
    }

    // неподдерживается тип сообщения
    private void setUnsupportedMessageView(Update update) {
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    // на отправку сообщения пользователю
    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void setFileReceivedView(Update update) {
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Происходит обработка...");
        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(update, PHOTO_MESSAGE_UPDATE);
        setFileReceivedView(update);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(update, DOC_MESSAGE_UPDATE);
        setFileReceivedView(update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(update, TEXT_MESSAGE_UPDATE);
    }
}
