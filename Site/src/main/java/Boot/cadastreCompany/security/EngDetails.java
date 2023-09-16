package Boot.cadastreCompany.security;

import Boot.cadastreCompany.dto.EngineerDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;

public class EngDetails implements UserDetails {

    private final EngineerDTO engineerDTO;

    public EngineerDTO getEngineerDTO() {
        return engineerDTO;
    }

    public EngDetails(EngineerDTO engineer) {
        this.engineerDTO = engineer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return engineerDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return engineerDTO.getLogin();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
