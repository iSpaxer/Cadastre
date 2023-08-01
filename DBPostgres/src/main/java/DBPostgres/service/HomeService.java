package DBPostgres.service;


//import DBPostgres.models.Client
import DBPostgres.models.Client;
import DBPostgres.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//import static rabitmq.RabbitQueue.TEXT_MESSAGE_UPDATE;


@Service
//@Transactional(readOnly = true)
public class HomeService {
    private HomeRepository homeRepository;
    //private final NotificationProducer notificationProducer;


    @Autowired
    public HomeService(HomeRepository repository) {
        this.homeRepository = repository;
        //this.notificationProducer = updateProducer;
    }

    public List<Client> getAllClient() {
        return homeRepository.findAll();
    }

    public Client getLastClient() {
        //return homeRepository.findById(homeRepository.count()).get();
        return homeRepository.findFirstByOrderByIdDesc();
    }

    //@Transactional
    public void save(Client newClient) {
        newClient.setCreatedData(new Date());

        System.out.printf(newClient.toString());
        homeRepository.save(newClient);
    }

//    private String information(Client client) {
//        return "A new client  left a request!\n" +
//                "Name: " + client.getName() + "\n" +
//                "Phone: " + client.getPhone() + "\n" +
//                "Data of creation: " + client.getCreatedData();
//    }
}
