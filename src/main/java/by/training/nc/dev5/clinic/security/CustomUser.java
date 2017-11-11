package by.training.nc.dev5.clinic.security;

import org.springframework.security.core.GrantedAuthority;
import by.training.nc.dev5.clinic.entities.User;
import java.util.Collection;

/**
 * Created by user on 01.06.2017.
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), authorities);
        this.user = user;
    }

    public CustomUser(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public String getUserLogin(){
        return user.getLogin();
    }
}
