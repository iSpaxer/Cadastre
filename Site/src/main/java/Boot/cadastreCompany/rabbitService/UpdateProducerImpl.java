package Boot.cadastreCompany.rabbitService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdateProducerImpl{
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void produce(String rabbitQueue, String str) {

        rabbitTemplate.convertAndSend(rabbitQueue, str);
       
    }

}
