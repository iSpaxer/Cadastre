package Boot.cadastreCompany.service.rabbit.impl;

import Boot.cadastreCompany.service.rabbit.NotificationProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerImpl implements NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueue, String str) {

        rabbitTemplate.convertAndSend(rabbitQueue, str);
    }

}
