package Boot.cadastreCompany.service.rabbit.impl;

import Boot.cadastreCompany.service.rabbit.GetListClientsProducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import utils.models.Client;

import java.util.List;

@Service
public class GetListClientsProducerIml implements GetListClientsProducer {
    private final RabbitTemplate rabbitTemplate;

    public GetListClientsProducerIml(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAll(String rabbitQueue, List<Client> clients) {
        rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(clients));
    }

    @Override
    public void produceCount(String rabbitQueue, List<Client> clients, int count) {
        //TODO возможно выйдем за предела списка!
        if (count >= clients.size()) {
            rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(clients));
        } else {
            List<Client> limitClients = clients.subList(0, count + 1);
            rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(limitClients));
        }

    }

    private String conversionClients(List<Client> clients) {
        String send = "";
        int count = 1;
        for (Client client : clients) {
            String s =  "Number: " + count +"\n" +
                        "Name: " + client.getName() + "\n" +
                        "Phone: " + client.getPhone() + "\n" +
                        "Data of creation: " + client.getCreatedData() + "\n'" +
                        "-------------" + "\n";
            count++;
            send += s;
        }
        return send;
    }

}
