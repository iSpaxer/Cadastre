package DBPostgres.service;

import DBPostgres.dto.EngineerDTO;
import DBPostgres.dto.EngineerUpdatePasswordDTO;
import DBPostgres.exception.EngineerNotAuthentication;
import DBPostgres.models.Engineer;
import DBPostgres.repositories.EngRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<EngineerDTO> findByEngineerDTOLogin(String login) {
        return Optional.of(engRepository.findByLogin(login).get().mappingEngineerDTO());
    }

    public Optional<Engineer> findByEngineerLogin(String login) {
        return engRepository.findByLogin(login);
    }

    public void save(Engineer engineer) {

        ///TODO security config
        engineer.setPassword(bCryptPasswordEncoder.encode(engineer.getPassword()));
        engRepository.save(engineer);
    }



    public Boolean authenticationEngineer(EngineerDTO engineerDTO) {
        Optional<Engineer> dBOptionalEngineer = engRepository.findByLogin(engineerDTO.getLogin());
        if (dBOptionalEngineer.isEmpty()) {
            log.info("Engineer in BD is empty!");
            return false;
            //TODO throw new EngineerNotAuthentication();
        }
        Optional<EngineerDTO> dBEngineerDTO = Optional.of(dBOptionalEngineer.get().mappingEngineerDTO());

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

    @Transactional
    public void updatePassword(EngineerUpdatePasswordDTO engineerUpdatePasswordDTO) {
        if (authenticationEngineer(new EngineerDTO(engineerUpdatePasswordDTO.getLogin(), engineerUpdatePasswordDTO.getOldPassword()))) {
            Optional<Engineer> dBOptionalEngineer = engRepository.findByLogin(engineerUpdatePasswordDTO.getLogin());
            Engineer upEngineer = dBOptionalEngineer.get();
            upEngineer.setPassword(bCryptPasswordEncoder.encode(engineerUpdatePasswordDTO.getNewPassword()));

            engRepository.save(upEngineer);
        } else {
            throw new EngineerNotAuthentication("Impossible update password! Engineer not authentication!");
        }
    }
}
