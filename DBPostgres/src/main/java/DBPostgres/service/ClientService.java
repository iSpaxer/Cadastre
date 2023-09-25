package DBPostgres.service;

import DBPostgres.dto.ClientDbDTO;
import DBPostgres.dto.EngineerLoginDTO;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDbDTO> getAllClient();
    Client getLastClient();
    Optional<Client> findById(ClientDbDTO clientDbDTOExternal);
    void save(Client newClient);
}
