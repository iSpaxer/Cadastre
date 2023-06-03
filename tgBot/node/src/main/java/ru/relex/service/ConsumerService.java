package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessageUpdates(Update update);

    //TODO SPAM
    //void consumeTextMessageUpdates(String str);

    void consumeDocMessageUpdates(Update update);
    void consumePhotoMessageUpdates(Update update);
    void consumeAnswerGetClientString(String infoClient);
}
