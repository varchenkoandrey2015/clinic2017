package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.ConfigConstants;
import by.training.nc.dev5.clinic.constants.MessageConstants;
import by.training.nc.dev5.clinic.constants.Parameters;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.Diagnosis;
import by.training.nc.dev5.clinic.entities.prescribings.Drug;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.enums.UserType;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.managers.PagePathManager;
import by.training.nc.dev5.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class DoctorController {

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

    @RequestMapping(value = "/doctormenu", method = RequestMethod.GET)
    public String showDoctorMenuForm(){
        return pagePathManager.getProperty(ConfigConstants.DOCTOR_MENU);
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.GET)
    public String showAddPatientForm(){
        return  pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_PATIENT);
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.POST)
    public String addPatient(@RequestParam(value = Parameters.PATIENT_NAME, required = false) String name,
                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        HttpSession session = request.getSession();
        try{
            if(!name.isEmpty()){
                if(name.length()<= ConfigConstants.MAX_STRING_LENGTH) {
                    if (patientService.isNewPatient(name)) {
                        Patient patient = new Patient();
                        patient.setName(name);
                        patientService.add(patient);
                        List<Patient> list = patientService.getAll();
                        session.setAttribute(Parameters.PATIENTS_LIST, list);
                        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                        return "redirect:/choosepatient";
                    } else {
                        pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_PATIENT);
                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.PATIENT_EXISTS, null, locale));
                    }
                }else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_PATIENT);
                }
            } else{
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_PATIENT);
            }
        }catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.GET)
    public String showAddDiagnosisForm(){
        return pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DIAGNOSIS);
    }

    @RequestMapping(value = "/adddiagnosis", method = RequestMethod.POST)
    public String addDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_NAME, required = false) String name,
                               ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        HttpSession session = request.getSession();
        int patientId = Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID));
        try {
            if (!name.isEmpty()) {
                if(name.length()<= ConfigConstants.MAX_STRING_LENGTH) {
                    Diagnosis diagnosis = new Diagnosis();
                    Patient patient = patientService.getById(patientId);
                    diagnosis.setName(name);
                    diagnosis.setPatient(patient);
                    diagnosisService.add(diagnosis);
                    List<Diagnosis> list = diagnosisService.getByPatient(patient);
                    session.setAttribute(Parameters.DIAGNOSIS_LIST, list);
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/doctormenu";
                }else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DIAGNOSIS);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DIAGNOSIS);
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/adddrug", method = RequestMethod.GET)
    public String showAddDrugForm(){
        return pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DRUG);
    }

    @RequestMapping(value = "/adddrug", method = RequestMethod.POST)
    public String addDrug(@RequestParam(value = Parameters.DRUG_NAME, required = false) String name,
                          ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        HttpSession session = request.getSession();
        int patientId = Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID));
        try {
            if (!name.isEmpty()) {
                if(name.length()<= ConfigConstants.MAX_STRING_LENGTH) {
                    Drug drug = new Drug();
                    Patient patient = patientService.getById(patientId);
                    drug.setName(name);
                    drug.setPatient(patient);
                    drugService.add(drug);
                    List<Drug> list = drugService.getByPatient(patient);
                    session.setAttribute(Parameters.DRUGS_LIST, list);
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/doctormenu";
                }else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DRUG);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_DRUG);
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addmedprocedure", method = RequestMethod.GET)
    public String showAddMedProcedureForm(){
        return pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_MEDPROCEDURE);
    }

    @RequestMapping(value = "/addmedprocedure", method = RequestMethod.POST)
    public String addMedProcedure(@RequestParam(value = Parameters.MEDPROCEDURE_NAME, required = false) String name,
                                  ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        HttpSession session = request.getSession();
        int patientId = Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID));
        try {
            if (!name.isEmpty()) {
                if(name.length()<= ConfigConstants.MAX_STRING_LENGTH) {
                    MedProcedure medProcedure = new MedProcedure();
                    Patient patient = patientService.getById(patientId);
                    medProcedure.setName(name);
                    medProcedure.setPatient(patient);
                    medProcedureService.add(medProcedure);
                    List<MedProcedure> list = medProcedureService.getByPatient(patient);
                    session.setAttribute(Parameters.MEDPROCEDURES_LIST, list);
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/doctormenu";
                }else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_MEDPROCEDURE);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_MEDPROCEDURE);
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addsurgery", method = RequestMethod.GET)
    public String showAddSurgeryForm(){
        return pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_SURGERY);
    }

    @RequestMapping(value = "/addsurgery", method = RequestMethod.POST)
    public String addSurgery(@RequestParam(value = Parameters.SURGERY_NAME, required = false) String name,
                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        String pagePath;
        HttpSession session = request.getSession();
        int patientId = Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID));
        try {
            if (!name.isEmpty()) {
                if(name.length()<= ConfigConstants.MAX_STRING_LENGTH) {
                    Surgery surgery = new Surgery();
                    Patient patient = patientService.getById(patientId);
                    surgery.setName(name);
                    surgery.setPatient(patient);
                    surgeryService.add(surgery);
                    List<Surgery> list = surgeryService.getByPatient(patient);
                    session.setAttribute(Parameters.SURGERIES_LIST, list);
                    redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                    return "redirect:/doctormenu";
                }else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_SURGERY);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.DOCTOR_ADD_SURGERY);
            }
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/deldiagnosis", method = RequestMethod.POST)
    public String delDiagnosis(@RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String id,
                               HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                diagnosisService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Diagnosis> list = diagnosisService.getByPatient(patient);
                session.setAttribute(Parameters.DIAGNOSIS_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/doctormenu";
        }catch (DAOException e){
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delsurgery", method = RequestMethod.POST)
    public String delSurgery(@RequestParam(value = Parameters.SURGERY_ID, required = false) String id,
                             HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        try {
            if (id != null) {
                surgeryService.delete(Integer.valueOf(id));
                Patient patient = patientService.getById(Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID)));
                List<Surgery> list = surgeryService.getByPatient(patient);
                session.setAttribute(Parameters.SURGERIES_LIST, list);
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/doctormenu";
        }catch (DAOException e){
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatient", method = RequestMethod.POST)
    public String delPatient(HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        int patientId = Integer.valueOf((String) session.getAttribute(Parameters.PATIENT_ID));
        try {
            patientService.delete(patientId);
            List<Patient> list = patientService.getAll();
            session.setAttribute(Parameters.PATIENTS_LIST, list);
            redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            return "redirect:/choosepatient";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }
}