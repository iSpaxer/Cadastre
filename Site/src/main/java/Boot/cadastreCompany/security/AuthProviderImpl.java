package Boot.cadastreCompany.security;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import Boot.cadastreCompany.service.ApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

///TODO нужно отправлять данные на БД и уже на бд сравнивать пароли
public class AuthProviderImpl { //implements AuthenticationProvider {

//    private final ApiRequestService apiRequestService;
//
//    @Autowired
//    public AuthProviderImpl(ApiRequestService apiRequestService) {
//        this.apiRequestService = apiRequestService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String login = authentication.getName();
//
//        Optional<EngineerDTO> optionalEngineerDTO = apiRequestService.findByEngineer(login);
//        if (optionalEngineerDTO.isEmpty()) {
//           /// throw new AuthenticationError()
//        }
//       // UserDetails userDetails = optionalEngineerDTO.get(;
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
}
