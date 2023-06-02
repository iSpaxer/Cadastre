package ru.relex.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.ConsumerService;
import ru.relex.service.MainService;
import ru.relex.service.ProducerService;

import static rabitmq.RabbitQueue.*;


@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    @Autowired
    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE: Text message is received");
        mainService.processTextMessage(update);
//        var message = update.getMessage();
//        var sendMessage = new SendMessage();
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setText("Hello from NODE");
//
//        producerService.producerAnswer(sendMessage);
    }

    //TODO SPAM
//    @Override
//    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
//    public void consumeSpamMessageString(String str) {
//        log.debug("NODE: Text message is received");
//
//
//        mainService.spam(str);
//    }

    @Override
    @RabbitListener(queues = ANSWER_CLIENTS)
    public void consumeAnswerGetClientString (String str) {
        log.debug("NODE: Clients is received");


        mainService.spam(str);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
	log.debug("NODE: Doc message is received");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
	log.debug("NODE: Photo message is received");
    }
}
