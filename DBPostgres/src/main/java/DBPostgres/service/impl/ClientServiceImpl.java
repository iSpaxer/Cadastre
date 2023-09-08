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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
    public List<ClientDbDTO> getAllClient() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientDbDTO> clientDbDTOList = new ArrayList<>();
        for (Client client : clientList) {
            ClientDbDTO clientDbDTO = modelMapper.map(client, ClientDbDTO.class);
            Engineer engineer = client.getEngineer();
            if (engineer != null) {
                clientDbDTO.setEngName(engineer.getName());
            }
            clientDbDTOList.add(clientDbDTO);
        }
        return clientDbDTOList;
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



//    private String information(Client client) {
//        return "A new client  left a request!\n" +
//                "Name: " + client.getName() + "\n" +
//                "Phone: " + client.getPhone() + "\n" +
//                "Data of creation: " + client.getCreatedData();
//    }
}
