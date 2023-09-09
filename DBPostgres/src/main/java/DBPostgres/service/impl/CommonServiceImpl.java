package DBPostgres.service.impl;

import DBPostgres.dto.ClientDbDTO;
import DBPostgres.dto.EngineerLoginDTO;
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
    public void checkForTakeClient(EngineerLoginDTO engineerLoginDTO, ClientDbDTO clientDbDTO) {
        Optional<Client> clientOptional = clientService.findById(clientDbDTO);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            if (client.getEngineer() == null) {
                Optional<Engineer> optionalEngineer = engService.findByEngineerLogin(engineerLoginDTO.getLogin());

                if (optionalEngineer.isPresent()) {
                    client.setEngineer(optionalEngineer.get());
                    clientService.save(client);
                } else {
                    ///TODO exception
                }
            }
        }
    }

}
