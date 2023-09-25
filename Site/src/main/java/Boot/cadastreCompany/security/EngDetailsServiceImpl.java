package Boot.cadastreCompany.security;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.UnknownException;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.service.ApiRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EngDetailsServiceImpl implements UserDetailsService {

    private ApiRequestService apiRequestService;

    @Autowired
    public EngDetailsServiceImpl(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DBRequestException, UnknownException {
        EngineerDTO engineerDTO;
        try {
           // System.out.println("1111111111111111111111111111111111111111111111111");
            engineerDTO = apiRequestService.findByEngineer(login);
//            log.info();
        } catch (DBRequestException e) {
            ///TODO лучше как то обработать исключение, чем просто его пробрасывать дальше в спринг
            throw e;
        } catch (UnknownException e) {
            throw e;
        }

        if (engineerDTO == null)
            throw new UsernameNotFoundException("Eng not found!");

        UserDetails userDetails = new EngDetails(engineerDTO);
        return userDetails;
    }
}
