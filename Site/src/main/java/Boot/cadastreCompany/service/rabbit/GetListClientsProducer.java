package Boot.cadastreCompany.service.rabbit;

import Boot.cadastreCompany.models.Client;

import java.util.List;

public interface GetListClientsProducer {
    void produceAll(String rabbitQueue, List<Client> clients);
    void produceCount(String rabbitQueue, List<Client> clients, int count);
}
