package DBPostgres.service;

import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientService {
    Page<ClientDbDTO> getAllClients(Pageable pageable);
    Client getLastClient();
    Optional<Client> findById(ClientDbDTO clientDbDTOExternal);
    Optional<Client> findById(Integer id);
    Client save(Client newClient);

    Page<ClientDbDTO> getClientsWithBetweenDate(String fromDate, String toDate,Pageable pageable);

    Page<ClientDbDTO> getActiveClients(Pageable pageable);

    Page<ClientDbDTO> getEndedClients(Pageable pageable);
}
