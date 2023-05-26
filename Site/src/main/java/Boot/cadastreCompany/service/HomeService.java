package Boot.cadastreCompany.service;

import Boot.cadastreCompany.models.Client;
import Boot.cadastreCompany.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HomeService {
    private HomeRepository repository;

    @Autowired
    public HomeService(HomeRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAllClient() {
        return repository.findAll();
    }

    @Transactional
    public void save(Client newClient) {
        newClient.setCreatedData(new Date());
        repository.save(newClient);
    }
}
