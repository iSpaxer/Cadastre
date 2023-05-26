package ru.tg.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {
    void produce(Update update, String rabbitQueue);
}
