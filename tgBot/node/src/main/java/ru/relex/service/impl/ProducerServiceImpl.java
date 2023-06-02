package ru.relex.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.relex.service.ProducerService;

import static rabitmq.RabbitQueue.*;


@Service
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
	this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void producerAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void producerAnswerClient() {
        rabbitTemplate.convertAndSend(REQUEST_GET_CLIENTS, true);
    }

}
