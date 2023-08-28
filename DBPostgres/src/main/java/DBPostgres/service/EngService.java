package DBPostgres.service;

import DBPostgres.dto.EngineerDTO;
import DBPostgres.models.Engineer;
import DBPostgres.repositories.EngRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EngService {
    private EngRepository engRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EngService(EngRepository engRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.engRepository = engRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Engineer> getAllEngineer() {
        ///TODO необходимо вытаскивать без паролей или например только имена
        return engRepository.findAll();
    }

    public Optional<EngineerDTO> findByEngineerLogin(String login) {
        return Optional.of(engRepository.findByLogin(login).get().mappingEngineerDTO());
    }

    public void save(Engineer engineer) {

        ///TODO security config
        engRepository.save(engineer);
    }

    public Boolean authenticationEngineer(EngineerDTO engineerDTO) {
        Optional<Engineer> optionalEngineer = engRepository.findByLogin(engineerDTO.getLogin());
        if (optionalEngineer.isEmpty()) {
            return false;
        }
        Optional<EngineerDTO> dBEngineerDTO = Optional.of(optionalEngineer.get().mappingEngineerDTO());

        log.info(dBEngineerDTO.get().getPassword() + " " + engineerDTO.getPassword() + " \n");
        if (bCryptPasswordEncoder.matches(
                engineerDTO.getPassword(),
                dBEngineerDTO.get().getPassword()
        )) {
            return true;
        } else {
            return false;
        }
    }
}
