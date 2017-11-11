package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.ConfigConstants;
import by.training.nc.dev5.clinic.managers.PagePathManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by user on 02.06.2017.
 */
@Controller
public class NurseController {
    @Autowired
    private PagePathManager pagePathManager;

    @RequestMapping(value = "/nursemenu", method = RequestMethod.GET)
    public String showNurseMenuForm(){
        return pagePathManager.getProperty(ConfigConstants.NURSE_MENU);
    }
}
