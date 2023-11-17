package DBPostgres.service;

import DBPostgres.dto.engineer.EngineerDTO;
import DBPostgres.dto.engineer.EngineerTelegramDTO;
import DBPostgres.dto.engineer.EngineerUpdatePasswordDTO;
import DBPostgres.exception.EngineerNotAuthentication;
import DBPostgres.models.Engineer;
import DBPostgres.repositories.EngRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EngService {
    private final EngRepository engRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public EngService(EngRepository engRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.engRepository = engRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
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

        if (bCryptPasswordEncoder.matches(
                engineerDTO.getPassword(),
                dBEngineerDTO.get().getPassword()
        )) {
            return true;
        } else {
            return false;
        }
    }

    // TODO return JWT and Save TgId
    public Boolean authenticationEngineerTelegram(EngineerTelegramDTO engineerTelegramDTO) {
        Optional<Engineer> dBOptionalEngineer = engRepository.findByLogin(engineerTelegramDTO.getLogin());
        if (dBOptionalEngineer.isEmpty()) {
            log.info("Engineer in BD is empty!");
            return false;
            //TODO throw new EngineerNotAuthentication();
        }
        Engineer engineer = dBOptionalEngineer.get();
        if (bCryptPasswordEncoder.matches(
                engineerTelegramDTO.getPassword(),
                engineer.getPassword()
        )) {
            engineer.setTgId(engineerTelegramDTO.getTgId());
            engRepository.save(engineer);
            return true;
        } else {
            return false;
        }
    }

    public Boolean telegramIsActive(Long tgId) {
        Optional<Engineer> dBOptionalEngineer = engRepository.findByTgId(tgId);
        if (dBOptionalEngineer.isEmpty()) {
            log.info("Engineer with tgId: "+ tgId + " in BD is empty!");
            return false;
        }
        return true;
    }

    public Optional<Engineer> findByTgId(Long tgId) {
        Optional<Engineer> dBOptionalEngineer = engRepository.findByTgId(tgId);
        return dBOptionalEngineer;
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


    public Optional<Long[]> getAllEngineersWithTgId() {
        return engRepository.findAllEngineersWithTgId();
    }
}
