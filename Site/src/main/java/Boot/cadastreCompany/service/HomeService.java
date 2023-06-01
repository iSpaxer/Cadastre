package Boot.cadastreCompany.service;

import Boot.cadastreCompany.models.Client;
import Boot.cadastreCompany.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Boot.cadastreCompany.rabbitService.UpdateProducerImpl;


import java.util.Date;
import java.util.List;

import static rabitmq.RabbitQueue.TEXT_MESSAGE_UPDATE;


@Service
@Transactional(readOnly = true)
public class HomeService {
    private HomeRepository repository;
    private final UpdateProducerImpl updateProducer;


    @Autowired
    public HomeService(HomeRepository repository, UpdateProducerImpl updateProducer) {
        this.repository = repository;
        this.updateProducer = updateProducer;
    }

    public List<Client> getAllClient() {
        return repository.findAll();
    }


    @Transactional
    public void save(Client newClient) {
        newClient.setCreatedData(new Date());
        updateProducer.produce(TEXT_MESSAGE_UPDATE, "text");
        repository.save(newClient);
    }
}
