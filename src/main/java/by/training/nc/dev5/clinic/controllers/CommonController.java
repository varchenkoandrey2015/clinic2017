package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.AccessLevels;
import by.training.nc.dev5.clinic.constants.ConfigConstants;
import by.training.nc.dev5.clinic.constants.MessageConstants;
import by.training.nc.dev5.clinic.constants.Parameters;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.entities.prescribings.Diagnosis;
import by.training.nc.dev5.clinic.entities.prescribings.Drug;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.enums.UserType;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.managers.PagePathManager;
import by.training.nc.dev5.clinic.services.*;
import by.training.nc.dev5.clinic.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class CommonController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDiagnosisService diagnosisService;
    @Autowired
    private IDrugService drugService;
    @Autowired
    private IMedProcedureService medProcedureService;
    @Autowired
    private IPatientService patientService;
    @Autowired
    private ISurgeryService surgeryService;
    @Autowired
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PrincipalUtil principalUtil;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndexPage(){
        return pagePathManager.getProperty(ConfigConstants.INDEX_PAGE_PATH);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String showErrorPage(){
        return pagePathManager.getProperty(ConfigConstants.ERROR_PAGE_PATH);
    }

    @RequestMapping(value = "/choosepatient", method = RequestMethod.GET)
    public String showChoosePatientForm(HttpServletRequest request){
        try{
            request.getSession().setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
            return pagePathManager.getProperty(ConfigConstants.SHOW_PATIENTS_PAGE);
        }catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/choosepatient", method = RequestMethod.POST)
    public String choosePatient(ModelMap model,
                                @RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId,
                                HttpServletRequest request, Locale locale){
        String pagePath;
        HttpSession session = request.getSession();
        try{
            if (patientId != null) {
                User user = userService.getByLogin(principalUtil.getPrincipal().getLogin());
                String patientName = patientService.getById(Integer.valueOf(patientId)).getName();
                session.setAttribute(Parameters.PATIENT_ID, patientId);
                session.setAttribute(Parameters.PATIENT_NAME, patientName);
                List<Diagnosis> diagnosises = diagnosisService.getByPatient(patientService.getById(Integer.valueOf(patientId)));
                List<Drug> drugs = drugService.getByPatient(patientService.getById(Integer.valueOf(patientId)));
                List<MedProcedure> medProcedures = medProcedureService.getByPatient(patientService.getById(Integer.valueOf(patientId)));
                List<Surgery> surgeries = surgeryService.getByPatient(patientService.getById(Integer.valueOf(patientId)));
                session.setAttribute(Parameters.DIAGNOSIS_LIST, diagnosises);
                session.setAttribute(Parameters.DRUGS_LIST, drugs);
                session.setAttribute(Parameters.MEDPROCEDURES_LIST, medProcedures);
                session.setAttribute(Parameters.SURGERIES_LIST, surgeries);
                if (user.getAccessLevel().equals(AccessLevels.DOCTOR)){
                    return "redirect:/doctormenu";
                } else{
                    return "redirect:/nursemenu";
                }
            }else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.SHOW_PATIENTS_PAGE);
            }
        }catch (DAOException e){
            return "redirect:/error";}
        return pagePath;
    }

    @RequestMapping(value = "/deldrug", method = RequestMethod.POST)
    public String delDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String id,
                          HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        try {
            User user = userService.getByLogin(principalUtil.getPrincipal().getLogin());
            if (id != null) {
                drugService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Drug> list = drugService.getByPatient(patient);
                session.setAttribute(Parameters.DRUGS_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            if (user.getAccessLevel().equals(AccessLevels.DOCTOR)) {
                return "redirect:/doctormenu";
            } else {
                return "redirect:/nursemenu";
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delmedprocedure", method = RequestMethod.POST)
    public String delMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String id,
                                  HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        try {
            User user = userService.getByLogin(principalUtil.getPrincipal().getLogin());
            if (id != null) {
                medProcedureService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<MedProcedure> list = medProcedureService.getByPatient(patient);
                session.setAttribute(Parameters.MEDPROCEDURES_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            if (user.getAccessLevel().equals(AccessLevels.DOCTOR)) {
                return "redirect:/doctormenu";
            } else {
                return "redirect:/nursemenu";
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
    }
}
