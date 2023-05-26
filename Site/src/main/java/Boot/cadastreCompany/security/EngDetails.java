package Boot.cadastreCompany.security;

import Boot.cadastreCompany.models.Engineer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class EngDetails implements UserDetails {

    private final Engineer engineer;

    public EngDetails(Engineer engineer) {
        this.engineer = engineer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return engineer.getPassword();
    }

    @Override
    public String getUsername() {
        return engineer.getLogin();
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
