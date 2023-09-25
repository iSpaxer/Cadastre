package ru.relex.service.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.FilterTextMessage;
import ru.relex.service.enums.ServiceCommands;

import static ru.relex.service.enums.ServiceCommands.*;

public class FilterTextMessageImpl implements FilterTextMessage {
    @Override
    public void filter(Update update) {
        Message userMessage = update.getMessage();
        String sms = userMessage.getText();

        if (CANCEL.equals(sms)) {
            //TODO возвращаем в начальное положение
        }
        else if (REGISTRATION.equals(sms)) {

        }
    }
}
