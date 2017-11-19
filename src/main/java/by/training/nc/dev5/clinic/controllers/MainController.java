package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.*;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.entities.prescribings.Diagnosis;
import by.training.nc.dev5.clinic.entities.prescribings.Drug;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
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
    private ISurgeryService surgeryService;
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
        try {
            request.getSession().setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
            request.getSession().setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
            request.getSession().setAttribute(Parameters.ALL_DRUGS, drugService.getAll());
            request.getSession().setAttribute(Parameters.ALL_MEDPROCEDURES, medProcedureService.getAll());
            request.getSession().setAttribute(Parameters.ALL_SURGERIES, surgeryService.getAll());
            return pagePathManager.getProperty(ConfigConstants.SHOW_MENU);
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public String choosePatient(ModelMap model,
                                @RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId,
                                HttpServletRequest request, Locale locale) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (patientId != null) {
                User user = userService.getByLogin(principalUtil.getPrincipal().getLogin());
                Patient patient = patientService.getById(Integer.valueOf(patientId));
                session.setAttribute(Parameters.PATIENT_ID, patientId);
                session.setAttribute(Parameters.PATIENT_NAME, patient.getName());
                session.setAttribute(Parameters.PATIENT_DIAGNOSIS_LIST, diagnosisService.getByPatient(patient));
                session.setAttribute(Parameters.PATIENT_DRUGS_LIST, drugService.getByPatient(patient));
                session.setAttribute(Parameters.PATIENT_MEDPROCEDURES_LIST, medProcedureService.getByPatient(patient));
                session.setAttribute(Parameters.PATIENT_SURGERIES_LIST, surgeryService.getByPatient(patient));
                if (user.getAccessLevel().equals(AccessLevels.DOCTOR)) {
                    session.setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
                    return "redirect:/doctormenu";
                } else {
                    return "redirect:/nursemenu";
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.SHOW_PATIENT);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/delpatientdrug", method = RequestMethod.POST)
    public String delPatientDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String id,
                          HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                drugService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Drug> list = drugService.getByPatient(patient);
                session.setAttribute(Parameters.PATIENT_DRUGS_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/doctormenu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatientmedprocedure", method = RequestMethod.POST)
    public String delPatientMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String id,
                                  HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                medProcedureService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<MedProcedure> list = medProcedureService.getByPatient(patient);
                session.setAttribute(Parameters.PATIENT_MEDPROCEDURES_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/nursemenu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.GET)
    public String showAddPatientForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.POST)
    public String addPatient(@RequestParam(value = Parameters.PATIENT_NAME, required = false) String name,
                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    if (patientService.isNewPatient(name)) {
                        Patient patient = new Patient();
                        patient.setName(name);
                        patientService.add(patient);
                        List<Patient> list = patientService.getAll();
                        session.setAttribute(Parameters.PATIENTS_LIST, list);
                        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                        return "redirect:/menu";
                    } else {
                        pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.PATIENT_EXISTS, null, locale));
                    }
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.GET)
    public String showAddDiagnosisForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_DIAGNOSIS);
    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.POST)
    public String addDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_NAME, required = false) String name,
                               ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    Diagnosis diagnosis = new Diagnosis();
                    diagnosis.setName(name);
                    diagnosisService.add(diagnosis);
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
    public String addDrug(@RequestParam(value = Parameters.DRUG_NAME, required = false) String name,
                          ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    Drug drug = new Drug();
                    drug.setName(name);
                    drugService.add(drug);
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
    public String addMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_NAME, required = false) String name,
                                  ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    MedProcedure medProcedure = new MedProcedure();
                    medProcedure.setName(name);
                    medProcedureService.add(medProcedure);
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

    @RequestMapping(value = "/addsurgery", method = RequestMethod.GET)
    public String showAddSurgeryForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_SURGERY);
    }

    @RequestMapping(value = "/addsurgery", method = RequestMethod.POST)
    public String addSurgery(@RequestParam(value = Parameters.SURGERY_NAME, required = false) String name,
                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!name.isEmpty()) {
                if (name.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    Surgery surgery = new Surgery();
                    surgery.setName(name);
                    surgeryService.add(surgery);
                    session.setAttribute(Parameters.ALL_SURGERIES, surgeryService.getAll());
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/menu";
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_SURGERY);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_SURGERY);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/delpatientdiagnosis", method = RequestMethod.POST)
    public String delPatientDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String id,
                               HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                diagnosisService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Diagnosis> list = diagnosisService.getByPatient(patient);
                session.setAttribute(Parameters.PATIENT_DIAGNOSIS_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/doctormenu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatientsurgery", method = RequestMethod.POST)
    public String delPatientSurgery(@RequestParam(value = Parameters.SURGERY_ID, required = false) String id,
                             HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                surgeryService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Surgery> list = surgeryService.getByPatient(patient);
                session.setAttribute(Parameters.PATIENT_SURGERIES_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/doctormenu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatient", method = RequestMethod.POST)
    public String delPatient(@RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            patientService.delete(Integer.valueOf(patientId));
            session.setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/deldiagnosis", method = RequestMethod.POST)
    public String delDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String diagnosisId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            diagnosisService.delete(Integer.valueOf(diagnosisId));
            session.setAttribute(Parameters.ALL_DIAGNOSISES, diagnosisService.getAll());
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/deldrug", method = RequestMethod.POST)
    public String delDrug(@RequestParam(value = Parameters.DRUG_ID, required = false) String drugId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            drugService.delete(Integer.valueOf(drugId));
            session.setAttribute(Parameters.ALL_DRUGS, drugService.getAll());
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delmedprocedure", method = RequestMethod.POST)
    public String delMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String medProcedureId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            medProcedureService.delete(Integer.valueOf(medProcedureId));
            session.setAttribute(Parameters.ALL_MEDPROCEDURES, medProcedureService.getAll());
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            return "redirect:/menu";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delsurgery", method = RequestMethod.POST)
    public String delSurgery(@RequestParam(value = Parameters.SURGERY_ID, required = false) String surgeryId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            surgeryService.delete(Integer.valueOf(surgeryId));
            session.setAttribute(Parameters.ALL_SURGERIES, surgeryService.getAll());
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
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
        String accessLevel = user.getAccessLevel();
        try {
            if (!login.isEmpty() & !password.isEmpty() & !(accessLevel == null)) {
                if (login.length() <= ConfigConstants.MAX_STRING_LENGTH && password.length() <= ConfigConstants.MAX_STRING_LENGTH && accessLevel.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                    if (userService.isNewUser(login)) {
                        if (accessLevel.equals(AccessLevels.DOCTOR) || accessLevel.equals(AccessLevels.NURSE)) {
                            userService.add(user);
                            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                            return "redirect:/index";
                        } else {
                            pagePath = pagePathManager.getProperty(ConfigConstants.REGISTRATION_PAGE_PATH);
                            model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.ACCESS_LEVEL, null, locale));
                        }
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