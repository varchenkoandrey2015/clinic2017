package by.training.nc.dev5.clinic.security;

import by.training.nc.dev5.clinic.constants.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 01.06.2017.
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private MessageSource messageSource;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response);

        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String targetUrl = "choosepatient";
        request.getSession().setAttribute(Parameters.WRONG_LOGIN_OR_PASSWORD, messageSource.getMessage("message.loginerror", null, request.getLocale()));
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }



    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session;
        session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
