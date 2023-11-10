package DBPostgres.service.impl;


//import DBPostgres.models.Client
import DBPostgres.dto.ClientDbDTO;
import DBPostgres.dto.EngineerLoginDTO;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.repositories.ClientRepository;
import DBPostgres.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
//@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, ModelMapper modelMapper) {
        this.clientRepository = repository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<ClientDbDTO> getAllClients(Pageable pageable) {
        Page<Client> clientPage = clientRepository.findAll(pageable);
        return customMapClientPageInDTO(clientPage);
    }

    private Page<ClientDbDTO> customMapClientPageInDTO(Page<Client> clientPage) {
        Page<ClientDbDTO> clientDbDTOPage = clientPage.map(client -> {
            ClientDbDTO clientDbDTO = modelMapper.map(client, ClientDbDTO.class);
            if (client.getEngineer() != null) {
                clientDbDTO.setEngName(client.getEngineer().getName());
            }
            return clientDbDTO;
        });
        return clientDbDTOPage;
    }

    @Transactional
    @Override
    public Page<ClientDbDTO> getClientsWithBetweenDate(String fromDateStr, String toDateStr, Pageable pageable) throws DateTimeException {
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);
        if (fromDate.isAfter(toDate)) {
            throw new DateTimeException("The end date is greater than the start date: " + fromDate.toString() + " > " +  toDate.toString());
        }
        Page<Client> clientPage = clientRepository.findAllClientsWithBetweenDate(fromDate, toDate, pageable);
        return customMapClientPageInDTO(clientPage);
    }

    @Override
    public Client getLastClient() {
        //return homeRepository.findById(homeRepository.count()).get();
        return clientRepository.findFirstByOrderByIdDesc();
    }

    @Override
    public Optional<Client> findById(ClientDbDTO clientDbDTOExternal) {
        return clientRepository.findById(clientDbDTOExternal.getId());
    }

//    @Override
//    public Boolean findById(EngineerLoginDTO engineerLoginDTO, ClientDbDTO clientDbDTOExternal) {
//        Optional<Client> clientOptional = clientRepository.findById(clientDbDTOExternal.getId());
//
//        if (clientOptional.isPresent()) {
//            Client client = clientOptional.get();
//            if (client.getEngineer() == null) {
//
//                client.setEngineer(modelMapper.map(engineerLoginDTO, Engineer.class));
//                clientRepository.save(client);
//                return true;
//            } else {
//                log.info("Дорогой %s. Клиента уже взяли!", engineerLoginDTO.getName());
//                //TODO exception
//                return false;
//            }
//        } else {
//            log.info("Не предвидиная ошибка в ClientService, findById");
//            //TODO exception
//            return false;
//        }
//    }

    @Override
    public void save(Client newClient) {
        newClient.setCreatedData(new Date());

        System.out.printf(newClient.toString());
        clientRepository.save(newClient);
    }

}
