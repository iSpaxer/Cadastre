package Boot.cadastreCompany.service.rabbit.impl;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.service.rabbit.GetListClientsProducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GetListClientsProducerIml implements GetListClientsProducer {
    private final RabbitTemplate rabbitTemplate;

    public GetListClientsProducerIml(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAll(String rabbitQueue, List<ClientDTO> clients) {
        rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(clients));
    }

    @Override
    public void produceCount(String rabbitQueue, List<ClientDTO> clients, int count) {
//        //TODO возможно выйдем за предела списка!
//        if (count >= clients.size()) {
//            rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(clients));
//        } else {
//            List<ClientDTO limitClients = clients.subList(0, count + 1);
//            rabbitTemplate.convertAndSend(rabbitQueue, conversionClients(limitClients));
//        }

    }

    private String conversionClients(List<ClientDTO> clients) {
        String send = "";
        int count = 1;
        for (ClientDTO client : clients) {
            String s =  "Number: " + count +"\n" +
                        "Name: " + client.getName() + "\n" +
                        "Phone: " + client.getPhone() + "\n" +
                        //"Data of creation: " + client.getCreatedData() + "\n'" +
                        "-------------" + "\n";
            count++;
            send += s;
        }
        return send;
    }

}
