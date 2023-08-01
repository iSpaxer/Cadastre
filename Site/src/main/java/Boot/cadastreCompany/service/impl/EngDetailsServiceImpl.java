package Boot.cadastreCompany.service.impl;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.security.EngDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EngDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        Optional<EngineerDTO> engineerDTO = engRepository.findByLogin(login);
//
//        if (engineerDTO.isEmpty())
//            throw new UsernameNotFoundException("Eng not found!");
//
//        return new EngDetails(engineerDTO.get());
        return null;
        //TODO
    }
}
