package DBPostgres.service.impl;

import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.dto.client.ClientForOutputTelegramDTO;
import DBPostgres.dto.client.ClientTakeTelegramDTO;
import DBPostgres.dto.engineer.EngineerLoginDTO;
import DBPostgres.exception.BodyEmptyException;
import DBPostgres.exception.ClientIsBusyAnotherEngineer;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.service.ClientService;
import DBPostgres.service.CommonService;
import DBPostgres.service.EngService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
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

    @Override
    public ClientForOutputTelegramDTO takeClientUsingTgbot(ClientTakeTelegramDTO clientTakeTelegramDTO) {
        Optional<Engineer> engineerOptional = engService.findByTgId(clientTakeTelegramDTO.getTgId());
        if (engineerOptional.isPresent()) {
            Optional<Client> clientOptional = clientService.findById(clientTakeTelegramDTO.getClientId());
            if (clientOptional.isPresent()) {
                Client isClient = clientOptional.get();
                if (isClient.getEngineer() == null) {
                    isClient.setEngineer(engineerOptional.get());
                    clientService.save(isClient);
                    ClientForOutputTelegramDTO clientForOutputTelegramDTO = new ClientForOutputTelegramDTO(
                            isClient.getId(),
                            isClient.getName(),
                            isClient.getCreatedData(),
                            isClient.getPhone(),
                            isClient.getEngineer().getName()
                    );
                    return clientForOutputTelegramDTO;
                } else {
                    log.error("ClientIsBusyAnotherEngineer");
                    throw new ClientIsBusyAnotherEngineer();
                }
            } else {
                log.error("clientOptional is not Present()");
                throw new UnknownError("clientOptional is Empty");
            }
        } else {
            log.error("engineerOptional is not Present()");
            throw new UnknownError("engineerOptional is Empty");
        }
    }

}
