package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.*;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.entities.Diagnosis;
import by.training.nc.dev5.clinic.entities.Drug;
import by.training.nc.dev5.clinic.entities.MedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.managers.PagePathManager;
import by.training.nc.dev5.clinic.services.*;
import by.training.nc.dev5.clinic.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 07.05.2017.
 */
@Controller
public class MainController {

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
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PrincipalUtil principalUtil;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndexPage() {
        return pagePathManager.getProperty(ConfigConstants.INDEX_PAGE_PATH);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String showErrorPage() {
        return pagePathManager.getProperty(ConfigConstants.ERROR_PAGE_PATH);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String showMenuForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            session.setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
            session.setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
            session.setAttribute(Parameters.ALL_DRUGS, drugService.getAll());
            session.setAttribute(Parameters.ALL_MEDPROCEDURES, medProcedureService.getAll());
            return pagePathManager.getProperty(ConfigConstants.SHOW_MENU);
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.GET)
    public String showAddPatientForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
    }

//    @RequestMapping(value = "/addpatient", method = RequestMethod.POST)
//    public String addPatient(@RequestParam(value = Parameters.PATIENT_NAME, required = false) String name,
//                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
//        String pagePath;
//        HttpSession session = request.getSession();
//        try {
//            if (!name.isEmpty()) {
//                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
//                    if (patientService.isNewPatient(name)) {
//                        Patient patient = new Patient();
//                        patient.setName(name);
//                        patientService.add(patient);
//                        List<Patient> list = patientService.getAll();
//                        session.setAttribute(Parameters.PATIENTS_LIST, list);
//                        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
//                        return "redirect:/menu";
//                    } else {
//                        pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
//                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.PATIENT_EXISTS, null, locale));
//                    }
//                } else {
//                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
//                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
//                }
//            } else {
//                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
//                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
//            }
//        } catch (DAOException e) {
//            return "redirect:/error";
//        }
//        return pagePath;
//    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.GET)
    public String showAddDiagnosisForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_DIAGNOSIS);
    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.POST)
    public String addDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String id,
                               @RequestParam(value = Parameters.DIAGNOSIS_NAME, required = false) String name,
                               @RequestParam(value = Parameters.DIAGNOSIS_DESC, required = false) String description,
                               ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    Diagnosis diagnosis = new Diagnosis();
                    diagnosis.setName(name);
                    diagnosis.setDescription(description);
                    if(!id.isEmpty())
                    {
                        diagnosis.setDiagnosisId(Integer.parseInt(id));
                        diagnosisService.update(diagnosis);
                    }else{
                        diagnosisService.add(diagnosis);
                    }
                    session.setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/menu";
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_DIAGNOSIS);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_DIAGNOSIS);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/adddrug", method = RequestMethod.GET)
    public String showAddDrugForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_DRUG);
    }

    @RequestMapping(value = "/adddrug", method = RequestMethod.POST)
    public String addDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String id,
                          @RequestParam(value = Parameters.DRUG_NAME, required = false) String name,
                          @RequestParam(value = Parameters.DRUG_DESC, required = false) String description,
                          ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    Drug drug = new Drug();
                    drug.setName(name);
                    drug.setDescription(description);
                    if(!id.isEmpty())
                    {
                        drug.setDrugId(Integer.parseInt(id));
                        drugService.update(drug);
                    }else{
                        drugService.add(drug);
                    }
                    session.setAttribute(Parameters.ALL_DRUGS, drugService.getAll());
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/menu";
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_DRUG);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_DRUG);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addmedprocedure", method = RequestMethod.GET)
    public String showAddMedProcedureForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_MEDPROCEDURE);
    }

    @RequestMapping(value = "/addmedprocedure", method = RequestMethod.POST)
    public String addMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String id,
                                  @RequestParam(value = Parameters.MEDPROCEDURE_NAME, required = false) String name,
                                  @RequestParam(value = Parameters.MEDPROCEDURE_DESC, required = false) String description,
                                  ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    MedProcedure medProcedure = new MedProcedure();
                    medProcedure.setName(name);
                    medProcedure.setDescription(description);
                    if(!id.isEmpty())
                    {
                        medProcedure.setMedProcedureId(Integer.parseInt(id));
                        medProcedureService.update(medProcedure);
                    }else{
                        medProcedureService.add(medProcedure);
                    }
                    session.setAttribute(Parameters.ALL_MEDPROCEDURES, medProcedureService.getAll());
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/menu";
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_MEDPROCEDURE);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_MEDPROCEDURE);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/editdiagnosis", method = RequestMethod.POST)
    public String editDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String diagnosisId,
                                HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (diagnosisId != null) {
                Diagnosis diagnosis = diagnosisService.getById(Integer.parseInt(diagnosisId));
                request.setAttribute(Parameters.DIAGNOSIS_ID, diagnosis.getDiagnosisId());
                request.setAttribute(Parameters.DIAGNOSIS_NAME, diagnosis.getName());
                request.setAttribute(Parameters.DIAGNOSIS_DESC, diagnosis.getDescription());
                return pagePathManager.getProperty(ConfigConstants.ADD_DIAGNOSIS);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/menu";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/editdrug", method = RequestMethod.POST)
    public String editDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String drugId,
                                HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (drugId != null) {
                Drug drug = drugService.getById(Integer.parseInt(drugId));
                request.setAttribute(Parameters.DRUG_ID,drug.getDrugId());
                request.setAttribute(Parameters.DRUG_NAME,drug.getName());
                request.setAttribute(Parameters.DRUG_DESC, drug.getDescription());
                return pagePathManager.getProperty(ConfigConstants.ADD_DRUG);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/menu";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/editmedprocedure", method = RequestMethod.POST)
    public String editMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String medProcedureId,
                                HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (medProcedureId != null) {
                MedProcedure medProcedure = medProcedureService.getById(Integer.parseInt(medProcedureId));
                request.setAttribute(Parameters.MEDPROCEDURE_ID, medProcedure.getMedProcedureId());
                request.setAttribute(Parameters.MEDPROCEDURE_NAME, medProcedure.getName());
                request.setAttribute(Parameters.MEDPROCEDURE_DESC, medProcedure.getDescription());
                return pagePathManager.getProperty(ConfigConstants.ADD_MEDPROCEDURE);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/menu";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatient", method = RequestMethod.POST)
    public String delPatient(@RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (patientId != null) {
                patientService.delete(Integer.valueOf(patientId));
                session.setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/deldiagnosis", method = RequestMethod.POST)
    public String delDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String diagnosisId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (diagnosisId != null) {
                diagnosisService.delete(Integer.valueOf(diagnosisId));
                session.setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/deldrug", method = RequestMethod.POST)
    public String delDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String drugId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (drugId != null) {
                drugService.delete(Integer.valueOf(drugId));
                session.setAttribute(Parameters.ALL_DRUGS, drugService.getAll());
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delmedprocedure", method = RequestMethod.POST)
    public String delMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String medProcedureId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (medProcedureId != null) {
                medProcedureService.delete(Integer.valueOf(medProcedureId));
                session.setAttribute(Parameters.ALL_MEDPROCEDURES, medProcedureService.getAll());
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/authfail", method = RequestMethod.GET)
    public String authFail(RedirectAttributes redirectAttributes, Locale locale) {
        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.WRONG_LOGIN_OR_PASSWORD, null, locale));
        return "redirect:/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model) {
        model.put(Parameters.USER, new User());
        return pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrate(@ModelAttribute("user") User user, ModelMap model, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        String login = user.getLogin();
        String password = user.getPassword();
        try {
            if (!login.isEmpty() & !password.isEmpty()) {
                if (login.length() <= ConfigConstants.MAX_STRING_LENGTH && password.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    if (userService.isNewUser(login)) {
                        userService.add(user);
                        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                        return "redirect:/index";
                    } else {
                        pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.USER_EXISTS, null, locale));
                    }
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }
}