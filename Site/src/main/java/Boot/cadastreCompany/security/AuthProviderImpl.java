package Boot.cadastreCompany.security;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.service.ApiRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

///TODO нужно отправлять данные на БД и уже на бд сравнивать пароли
@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private ApiRequestService apiRequestService;
    private ModelMapper modelMapper;

    @Autowired
    public AuthProviderImpl(ApiRequestService apiRequestService, ModelMapper modelMapper) {
        this.apiRequestService = apiRequestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EngineerDTO engineerDTO = new EngineerDTO(authentication.getName(), authentication.getCredentials().toString());
        if (!apiRequestService.authenticationEngineer(engineerDTO)) {
            throw new BadCredentialsException("Incorrect login or password");
        }

        return UsernamePasswordAuthenticationToken(modelMapper.map(engineerDTO, EngDetails.class), );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        ///TODO
        return true;
    }

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
