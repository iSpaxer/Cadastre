package DBPostgres.service;

import DBPostgres.models.Engineer;
import DBPostgres.repositories.EngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngService {
    private EngRepository engRepository;

    @Autowired
    public EngService(EngRepository engRepository) {
        this.engRepository = engRepository;
    }

    public List<Engineer> getAllEngineer() {
        ///TODO необходимо вытаскивать без паролей или например только имена
        return engRepository.findAll();
    }

    public void save(Engineer engineer) {

        ///TODO security config
        engRepository.save(engineer);
    }
}
