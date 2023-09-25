package DBPostgres.service.impl;
//
//import DBPostgres.models.Engineer;
//import DBPostgres.repositories.EngRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class EngDetailsServiceImpl implements UserDetailsService {
//
////    private final EngRepository engRepository;
////
////    @Autowired
////    public EngDetailsServiceImpl(EngRepository engRepository) {
////        this.engRepository = engRepository;
////    }
////
////    @Override
////    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
////        Optional<Engineer> eng = engRepository.findByLogin(login);
////
////        if (eng.isEmpty())
////            throw new UsernameNotFoundException("Eng not found!");
////        ///TODO
////        return null;
////        //return new EngDetails(eng.get());  // return new EngDetails(eng.get());
////    }
//}
