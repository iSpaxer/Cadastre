package Boot.cadastreCompany.service.rabbit_2_0;

import Boot.cadastreCompany.dto.ClientDTO;

public interface ClientProducer {
    void clientSaveProduce(String rabbitQueue, ClientDTO clientDTO);
}
