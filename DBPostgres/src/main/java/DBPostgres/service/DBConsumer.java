package DBPostgres.service;

import DBPostgres.dto.ClientDTO;

public interface DBConsumer {
    void clientConsume(ClientDTO clientDTO);
}
