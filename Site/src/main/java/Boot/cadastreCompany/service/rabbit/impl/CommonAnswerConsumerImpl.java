package Boot.cadastreCompany.service.rabbit.impl;
import Boot.cadastreCompany.service.rabbit.RabbitMainService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import Boot.cadastreCompany.service.rabbit.CommonAnswerConsumer;
import org.springframework.stereotype.Service;

import java.util.List;

import static rabitmq.RabbitQueue.REQUEST_GET_CLIENTS;

@Service
public class CommonAnswerConsumerImpl implements CommonAnswerConsumer {
    private final RabbitMainService rabbitMainService;

    public CommonAnswerConsumerImpl(RabbitMainService rabbitMainService) {
        this.rabbitMainService = rabbitMainService;
    }


    @Override
    public void consumeCheckLogin(List<String> loginPassword) {

    }

    @Override
    @RabbitListener(queues = REQUEST_GET_CLIENTS)
    public void consumeGetListClient(Boolean bool) {
        //TODO добавить счеткик?
        rabbitMainService.SendAllClients(0);
    }
}
