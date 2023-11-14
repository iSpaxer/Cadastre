package DBPostgres.service;

import DBPostgres.dto.client.ClientDTO;

public interface DBConsumer {
    void clientConsume(ClientDTO clientDTO);
}
