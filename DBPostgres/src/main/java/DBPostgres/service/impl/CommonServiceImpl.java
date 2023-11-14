package DBPostgres.service.impl;

import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.dto.engineer.EngineerLoginDTO;
import DBPostgres.exception.BodyEmptyException;
import DBPostgres.exception.ClientIsBusyAnotherEngineer;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.service.ClientService;
import DBPostgres.service.CommonService;
import DBPostgres.service.EngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommonServiceImpl implements CommonService {
    private ClientService clientService;
    private EngService engService;

    @Autowired
    public CommonServiceImpl(ClientService clientService, EngService engService) {
        this.clientService = clientService;
        this.engService = engService;
    }

    @Override
    public void checkForTakeClient(EngineerLoginDTO engineerLoginDTO, ClientDbDTO clientDbDTO) throws BodyEmptyException,
            ClientIsBusyAnotherEngineer, UnknownError{
        if (engineerLoginDTO.getLogin() == null) {
            throw new BodyEmptyException("Login is empty...");
        } if (clientDbDTO.getId() == null) {
            throw new BodyEmptyException("Client id is empty...");
        }
        Optional<Client> clientOptional = clientService.findById(clientDbDTO);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            if (client.getEngineer() == null) {
                Optional<Engineer> optionalEngineer = engService.findByEngineerLogin(engineerLoginDTO.getLogin());

                if (optionalEngineer.isPresent()) {
                    client.setEngineer(optionalEngineer.get());
                    clientService.save(client);
                } else {
                    throw new UnknownError();
                }
            } else {
                throw new ClientIsBusyAnotherEngineer();
            }
        }
    }

}
