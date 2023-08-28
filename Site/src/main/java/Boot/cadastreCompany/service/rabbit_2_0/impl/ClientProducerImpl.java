package Boot.cadastreCompany.service.rabbit_2_0.impl;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.service.rabbit_2_0.ClientProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientProducerImpl implements ClientProducer {

    private final RabbitTemplate rabbitTemplate;

    public ClientProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void clientSaveProduce(String rabbitQueue, ClientDTO clientDTO) {
      log.info("Sending client in Rabbit... ");
      rabbitTemplate.convertAndSend(rabbitQueue, clientDTO);
    }
}
