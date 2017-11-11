package by.training.nc.dev5.clinic.security;

import by.training.nc.dev5.clinic.constants.WebConstants;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IUserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 01.06.2017.
 */
@Service("authenticationService")
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private IUserService userService;

    public CustomUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.getByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException(WebConstants.USER_NOT_FOUND);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return new CustomUser(user, true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(WebConstants.ROLE_PREFIX + user.getAccessLevel()));
        return authorities;
    }
}
