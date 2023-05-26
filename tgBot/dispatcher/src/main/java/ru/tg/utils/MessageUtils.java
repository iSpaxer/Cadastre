package ru.tg.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageUtils {
    public SendMessage generateSendMessageWithText(Update update, String sendText) {
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(sendText);
        return sendMessage;
    }
}
