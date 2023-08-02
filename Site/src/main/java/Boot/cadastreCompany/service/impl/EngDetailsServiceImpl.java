package Boot.cadastreCompany.service.impl;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.service.ApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EngDetailsServiceImpl implements UserDetailsService {

    private ApiRequestService apiRequestService;

    @Autowired
    public EngDetailsServiceImpl(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<EngineerDTO> engineerDTO = apiRequestService.findByEngineer(login);

        if (engineerDTO.isEmpty())
            throw new UsernameNotFoundException("Eng not found!");

        return new EngDetails(engineerDTO.get());
    }
}
