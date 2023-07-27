package Boot.cadastreCompany.service;

import Boot.cadastreCompany.models.Client;
import Boot.cadastreCompany.repositories.HomeRepository;
import Boot.cadastreCompany.service.rabbit.NotificationProducer;
import Boot.cadastreCompany.service.rabbit.impl.NotificationProducerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static rabitmq.RabbitQueue.TEXT_MESSAGE_UPDATE;



@Service
@Transactional(readOnly = true)
public class HomeService {
    private HomeRepository repository;
    private final NotificationProducer notificationProducer;


    @Autowired
    public HomeService(HomeRepository repository, NotificationProducerImpl updateProducer) {
        this.repository = repository;
        this.notificationProducer = updateProducer;
    }

    public List<Client> getAllClient() {
        return repository.findAll();
    }


    @Transactional
    public void save(Client newClient) {
        newClient.setCreatedData(new Date());

        notificationProducer.produce(TEXT_MESSAGE_UPDATE, information(newClient));
        repository.save(newClient);
    }

    private String information(Client client) {
        return "A new client  left a request!\n" +
                "Name: " + client.getName() + "\n" +
                "Phone: " + client.getPhone() + "\n" +
                "Data of creation: " + client.getCreatedData();
    }
}
