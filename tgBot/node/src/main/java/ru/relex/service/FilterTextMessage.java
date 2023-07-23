package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface FilterTextMessage {
    void filter(Update update);
}
