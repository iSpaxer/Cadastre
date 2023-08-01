package DBPostgres.util.validate;

import DBPostgres.dto.EngineerDTO;
import DBPostgres.repositories.EngRepository;
import org.hibernate.annotations.Comment;
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
        EngineerDTO engineer = (EngineerDTO) target;
        ///TODO проверка на лёгкость пароля)
        if (!engRepository.findByLogin(engineer.getLogin()).isPresent()) {
            errors.reject("Login is already busy");
        }
    }
}
