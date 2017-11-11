package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.*;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.enums.UserType;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.managers.PagePathManager;
import by.training.nc.dev5.clinic.services.IPatientService;
import by.training.nc.dev5.clinic.services.IUserService;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.Map;

/**
 * Created by user on 07.05.2017.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/authfail", method = RequestMethod.GET)
    public String authFail(RedirectAttributes redirectAttributes, Locale locale) {
        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.WRONG_LOGIN_OR_PASSWORD, null, locale));
        return "redirect:/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        return "redirect:/index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model){
        model.put(Parameters.USER, new User());
        return pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrate(@ModelAttribute("user") User user, ModelMap model, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        String login = user.getLogin();
        String password = user.getPassword();
        String accessLevel=user.getAccessLevel();
        try{
            if(!login.isEmpty() & !password.isEmpty() & !(accessLevel==null)){
                if(login.length()<= ConfigConstants.MAX_STRING_LENGTH && password.length()<= ConfigConstants.MAX_STRING_LENGTH && accessLevel.length()<= ConfigConstants.MAX_STRING_LENGTH){
                    if (userService.isNewUser(login)){
                        if (accessLevel.equals(AccessLevels.DOCTOR) || accessLevel.equals(AccessLevels.NURSE)) {
                            userService.add(user);
                            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION , null, locale));
                            return "redirect:/index";
                        }else{
                            pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                            model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.ACCESS_LEVEL, null, locale));
                        }
                    }else{
                        pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.USER_EXISTS, null, locale));
                    }
                }else{
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                }
            }else{
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePath;
    }
}