package by.training.nc.dev5.clinic.utils;

import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.security.CustomUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by user on 01.06.2017.
 */
@Component
public class PrincipalUtil {
    public User getPrincipal(){
        User user = new User();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUser) {
            user.setLogin(((CustomUser) principal).getUserLogin());
        }
        return user;
    }
}
