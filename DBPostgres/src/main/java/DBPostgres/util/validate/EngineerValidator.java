package DBPostgres.util.validate;

import DBPostgres.dto.engineer.EngineerDTO;
import DBPostgres.dto.engineer.EngineerLoginDTO;
import DBPostgres.repositories.EngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EngineerValidator implements Validator {
    private EngRepository engRepository;

    @Autowired
    public EngineerValidator(EngRepository engRepository) {
        this.engRepository = engRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EngineerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EngineerLoginDTO engineerLoginDTO = (EngineerLoginDTO) target;
     //   Optional<Engineer> engineerDTOOptional = engRepository.findByLogin(engineerLoginDTO.getLogin());
        ///TODO проверка на лёгкость пароля)
        if (!engRepository.findByLogin(engineerLoginDTO.getLogin()).isEmpty()) {
            errors.reject("Login is already busy ");
        }
    }
}
