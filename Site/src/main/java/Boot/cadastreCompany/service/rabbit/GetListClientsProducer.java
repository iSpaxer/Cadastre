package Boot.cadastreCompany.service.rabbit;


import Boot.cadastreCompany.dto.ClientDTO;

import java.util.List;

public interface GetListClientsProducer {
    void produceAll(String rabbitQueue, List<ClientDTO> clients);
    void produceCount(String rabbitQueue, List<ClientDTO> clients, int count);
}
