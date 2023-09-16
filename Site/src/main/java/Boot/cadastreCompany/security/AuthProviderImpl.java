package Boot.cadastreCompany.security;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.service.ApiRequestService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
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
        log.info("authenticationProvider working... ");
        if (authentication.getName().isEmpty() || authentication.getPrincipal() == null) {
            throw new BadCredentialsException("Empty login or password... ");
        }
        EngineerDTO engineerDTO = new EngineerDTO(authentication.getName(), authentication.getCredentials().toString());

        if (!apiRequestService.authenticationEngineer(engineerDTO)) {
            throw new BadCredentialsException("Incorrect login or password... ");
        }
        ///TODO hidePassword?
//        hidePassword(engineerDTO);
        return new UsernamePasswordAuthenticationToken(new EngDetails(engineerDTO),
                authentication.getCredentials(), Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        ///TODO
        return true;
    }

    private void hidePassword(EngineerDTO engineerDTO) {
        engineerDTO.setPassword(null);
    }

}
