package Boot.cadastreCompany.service.rabbit;


import utils.models.Client;

import java.util.List;

public interface GetListClientsProducer {
    void produceAll(String rabbitQueue, List<Client> clients);
    void produceCount(String rabbitQueue, List<Client> clients, int count);
}
