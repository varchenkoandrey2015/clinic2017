package by.training.nc.dev5.clinic.controllers;

import by.training.nc.dev5.clinic.constants.*;
import by.training.nc.dev5.clinic.entities.*;
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
    private IPatientDiagnosisService patientDiagnosisService;
    @Autowired
    private IPatientDrugService patientDrugService;
    @Autowired
    private IPatientMedProcedureService patientMedProcedureService;
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

    @RequestMapping(value = "/showpatient", method = RequestMethod.GET)
    public String showPatient(@RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId,
                              HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if (patientId == null) {
            patientId = (String) session.getAttribute(Parameters.PATIENT_ID);
        }
        try {
            if (patientId != null) {
                Patient patient = patientService.getById(Integer.parseInt(patientId));
                session.setAttribute(Parameters.PATIENT_ID, patientId);
                session.setAttribute(Parameters.PATIENT_FIRSTNAME, patient.getFirstName());
                session.setAttribute(Parameters.PATIENT_MIDDLENAME, patient.getMiddleName());
                session.setAttribute(Parameters.PATIENT_LASTNAME, patient.getLastName());
                session.setAttribute(Parameters.PATIENT_DIAGNOSIS_LIST, patientDiagnosisService.getByPatient(patient));
                session.setAttribute(Parameters.PATIENT_DRUGS_LIST, patientDrugService.getByPatient(patient));
                session.setAttribute(Parameters.PATIENT_MEDPROCEDURES_LIST, patientMedProcedureService.getByPatient(patient));
                return pagePathManager.getProperty(ConfigConstants.SHOW_PATIENT);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/menu";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try{
            session.setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
        }catch (DAOException e){
            return "redirect:/error";
        }
        return pagePathManager.getProperty(ConfigConstants.SHOW_REPORT);
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.GET)
    public String showAddPatientForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.POST)
    public String addPatient(@RequestParam(value = Parameters.PATIENT_ID, required = false) String id,
                             @RequestParam(value = Parameters.PATIENT_FIRSTNAME, required = false) String firstName,
                             @RequestParam(value = Parameters.PATIENT_MIDDLENAME, required = false) String middleName,
                             @RequestParam(value = Parameters.PATIENT_LASTNAME, required = false) String lastName,
                             @RequestParam(value = Parameters.PATIENT_GENDER, required = false) String gender,
                             @RequestParam(value = Parameters.PATIENT_ADDRESS, required = false) String address,
                             @RequestParam(value = Parameters.PATIENT_PHONE, required = false) String phone,
                             ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (phone.startsWith("+375")) {
                if (!firstName.isEmpty() && !gender.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                    if (firstName.length() <= ConfigConstants.MAX_STRING_LENGTH && gender.length() <= ConfigConstants.MAX_STRING_LENGTH
                            && address.length() <= ConfigConstants.MAX_STRING_LENGTH && phone.length() <= ConfigConstants.MAX_STRING_LENGTH) {
                        Patient patient = new Patient();
                        patient.setFirstName(firstName);
                        patient.setMiddleName(middleName);
                        patient.setLastName(lastName);
                        patient.setGender(gender);
                        patient.setAddress(address);
                        patient.setPhone(phone);
                        if (!id.isEmpty()) {
                            patient.setPatientId(Integer.parseInt(id));
                            patientService.update(patient);
                        } else {
                            patientService.add(patient);
                        }
                        session.setAttribute(Parameters.PATIENTS_LIST, patientService.getAll());
                        redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                        return "redirect:/menu";

                    } else {
                        model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.TOO_LONG_STRING, null, locale));
                        pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
                    }
                } else {
                    model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                    pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
                }
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.WRONG_PHONE, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addpatientdiagnosis", method = RequestMethod.GET)
    public String showAddPatientDiagnosisForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DIAGNOSIS);
    }

    @RequestMapping(value = "/addpatientdiagnosis", method = RequestMethod.POST)
    public String addPatientDiagnosis(@RequestParam(value = Parameters.PATIENT_DIAGNOSIS_ID, required = false) String id,
                                      @RequestParam(value = Parameters.DIAGNOSIS_ID, required = false) String diagnosisId,
                                      @RequestParam(value = Parameters.PRESCRIBING_STARTDATE, required = false) String startDate,
                                      @RequestParam(value = Parameters.PRESCRIBING_ENDDATE, required = false) String endDate,
                                      ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!diagnosisId.isEmpty() && !startDate.isEmpty()) {
                PatientDiagnosis patientDiagnosis = new PatientDiagnosis();
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientDiagnosis.setPatient(patient);
                patientDiagnosis.setDiagnosis(diagnosisService.getById(Integer.parseInt(diagnosisId)));
                patientDiagnosis.setStartDate(startDate);
                patientDiagnosis.setEndDate(endDate);
                if (!id.isEmpty()) {
                    patientDiagnosis.setPatientDiagnosisId(Integer.parseInt(id));
                    patientDiagnosisService.update(patientDiagnosis);
                } else {
                    patientDiagnosisService.add(patientDiagnosis);
                }
                session.setAttribute(Parameters.PATIENT_DIAGNOSIS_LIST, patientDiagnosisService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                return "redirect:/showpatient";

            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DIAGNOSIS);
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addpatientdrug", method = RequestMethod.GET)
    public String showAddPatientDrugForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DRUG);
    }

    @RequestMapping(value = "/addpatientdrug", method = RequestMethod.POST)
    public String addPatientDrug(@RequestParam(value = Parameters.PATIENT_DRUG_ID, required = false) String id,
                                 @RequestParam(value = Parameters.DRUG_ID, required = false) String drugId,
                                 @RequestParam(value = Parameters.PRESCRIBING_STARTDATE, required = false) String startDate,
                                 @RequestParam(value = Parameters.PRESCRIBING_DAYS, required = false) String days,
                                 ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!drugId.isEmpty() && !startDate.isEmpty()) {
                PatientDrug patientDrug = new PatientDrug();
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientDrug.setPatient(patient);
                patientDrug.setDrug(drugService.getById(Integer.parseInt(drugId)));
                patientDrug.setStartDate(startDate);
                patientDrug.setDays(Integer.valueOf(days));
                if (!id.isEmpty()) {
                    patientDrug.setPatientDrugId(Integer.parseInt(id));
                    patientDrugService.update(patientDrug);
                } else {
                    patientDrugService.add(patientDrug);
                }
                session.setAttribute(Parameters.PATIENT_DRUGS_LIST, patientDrugService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                return "redirect:/showpatient";

            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DIAGNOSIS);
            }
        } catch (NumberFormatException nfe) {
            model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.WRONG_FORMAT, null, locale));
            pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DRUG);
        } catch (DAOException e) {
            return "redirect:/error";
        }
        return pagePath;
    }

    @RequestMapping(value = "/addpatientmedprocedure", method = RequestMethod.GET)
    public String showAddPatientMedProcedureForm() {
        return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_MEDPROCEDURE);
    }

    @RequestMapping(value = "/addpatientmedprocedure", method = RequestMethod.POST)
    public String addPatientMedProcedure(@RequestParam(value = Parameters.PATIENT_MEDPROCEDURE_ID, required = false) String id,
                                         @RequestParam(value = Parameters.MEDPROCEDURE_ID, required = false) String medProcedureId,
                                         @RequestParam(value = Parameters.PRESCRIBING_STARTDATE, required = false) String startDate,
                                         @RequestParam(value = Parameters.PRESCRIBING_COUNT, required = false) String count,
                                         ModelMap model, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        String pagePath;
        HttpSession session = request.getSession();
        try {
            if (!medProcedureId.isEmpty() && !startDate.isEmpty()) {
                PatientMedProcedure patientMedProcedure = new PatientMedProcedure();
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientMedProcedure.setPatient(patient);
                patientMedProcedure.setMedProcedure(medProcedureService.getById(Integer.parseInt(medProcedureId)));
                patientMedProcedure.setStartDate(startDate);
                if (count.equals("")) {
                    patientMedProcedure.setCount(1);
                } else {
                    patientMedProcedure.setCount(Integer.valueOf(count));
                }
                if (!id.isEmpty()) {
                    patientMedProcedure.setPatientMedProcedureId(Integer.parseInt(id));
                    patientMedProcedureService.update(patientMedProcedure);
                } else {
                    patientMedProcedureService.add(patientMedProcedure);
                }
                session.setAttribute(Parameters.PATIENT_MEDPROCEDURES_LIST, patientMedProcedureService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
                return "redirect:/showpatient";
            } else {
                model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_FIELDS, null, locale));
                pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_MEDPROCEDURE);
            }
        } catch (NumberFormatException nfe) {
            model.put(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.WRONG_FORMAT, null, locale));
            pagePath = pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_MEDPROCEDURE);
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
                    if (!id.isEmpty()) {
                        diagnosis.setDiagnosisId(Integer.parseInt(id));
                        diagnosisService.update(diagnosis);
                    } else {
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
                    if (!id.isEmpty()) {
                        drug.setDrugId(Integer.parseInt(id));
                        drugService.update(drug);
                    } else {
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
                    if (!id.isEmpty()) {
                        medProcedure.setMedProcedureId(Integer.parseInt(id));
                        medProcedureService.update(medProcedure);
                    } else {
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


    @RequestMapping(value = "/editpatient", method = RequestMethod.GET)
    public String editPatient(@RequestParam(value = Parameters.PATIENT_ID, required = false) String patientId,
                              HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (patientId != null) {
                Patient patient = patientService.getById(Integer.parseInt(patientId));
                request.setAttribute(Parameters.PATIENT_ID, patientId);
                request.setAttribute(Parameters.PATIENT_FIRSTNAME, patient.getFirstName());
                request.setAttribute(Parameters.PATIENT_MIDDLENAME, patient.getMiddleName());
                request.setAttribute(Parameters.PATIENT_LASTNAME, patient.getLastName());
                request.setAttribute(Parameters.PATIENT_GENDER, patient.getGender());
                request.setAttribute(Parameters.PATIENT_ADDRESS, patient.getAddress());
                request.setAttribute(Parameters.PATIENT_PHONE, patient.getPhone());
                return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/menu";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/editpatientdiagnosis", method = RequestMethod.GET)
    public String editPatientDiagnosis(@RequestParam(value = Parameters.PATIENT_DIAGNOSIS_ID, required = false) String patientDiagnosisId,
                                       HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (patientDiagnosisId != null) {
                PatientDiagnosis patientDiagnosis = patientDiagnosisService.getById(Integer.parseInt(patientDiagnosisId));
                request.setAttribute(Parameters.PATIENT_DIAGNOSIS_ID, patientDiagnosisId);
                request.setAttribute(Parameters.PATIENT_DIAGNOSIS_DIAGNOSIS, patientDiagnosis.getDiagnosis());
                request.setAttribute(Parameters.PRESCRIBING_STARTDATE, patientDiagnosis.getStartDate());
                request.setAttribute(Parameters.PRESCRIBING_ENDDATE, patientDiagnosis.getEndDate());
                return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DIAGNOSIS);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/patient";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/editpatientdrug", method = RequestMethod.GET)
    public String editPatientDrug(@RequestParam(value = Parameters.PATIENT_DRUG_ID, required = false) String patientDrugId,
                                  HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (patientDrugId != null) {
                PatientDrug patientDrug = patientDrugService.getById(Integer.parseInt(patientDrugId));
                request.setAttribute(Parameters.PATIENT_DRUG_ID, patientDrugId);
                request.setAttribute(Parameters.PATIENT_DRUG_DRUG, patientDrug.getDrug());
                request.setAttribute(Parameters.PRESCRIBING_STARTDATE, patientDrug.getStartDate());
                request.setAttribute(Parameters.PRESCRIBING_DAYS, String.valueOf(patientDrug.getDays()));
                return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_DRUG);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/patient";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/editpatientmedprocedure", method = RequestMethod.GET)
    public String editPatientMedProcedure(@RequestParam(value = Parameters.PATIENT_MEDPROCEDURE_ID, required = false) String patientMedProcedureId,
                                          HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        try {
            if (patientMedProcedureId != null) {
                PatientMedProcedure patientMedProcedure = patientMedProcedureService.getById(Integer.parseInt(patientMedProcedureId));
                request.setAttribute(Parameters.PATIENT_MEDPROCEDURE_ID, patientMedProcedureId);
                request.setAttribute(Parameters.PATIENT_MEDPROCEDURE_MEDPROCEDURE, patientMedProcedure.getMedProcedure());
                request.setAttribute(Parameters.PRESCRIBING_STARTDATE, patientMedProcedure.getStartDate());
                request.setAttribute(Parameters.PRESCRIBING_COUNT, String.valueOf(patientMedProcedure.getCount()));
                return pagePathManager.getProperty(ConfigConstants.ADD_PATIENT_MEDPROCEDURE);
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
                return "redirect:/patient";
            }
        } catch (DAOException e) {
            return "redirect:/error";
        }
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
                request.setAttribute(Parameters.DRUG_ID, drug.getDrugId());
                request.setAttribute(Parameters.DRUG_NAME, drug.getName());
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

    @RequestMapping(value = "/delpatientdiagnosis", method = RequestMethod.POST)
    public String delPatientDiagnosis(@RequestParam(value = Parameters.PATIENT_DIAGNOSIS_ID, required = false) String patientDiagnosisId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (patientDiagnosisId != null) {
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientDiagnosisService.delete(Integer.valueOf(patientDiagnosisId));
                session.setAttribute(Parameters.PATIENT_DIAGNOSIS_LIST, patientDiagnosisService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/showpatient";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatientdrug", method = RequestMethod.POST)
    public String delPatientDrug(@RequestParam(value = Parameters.PATIENT_DRUG_ID, required = false) String patientDrugId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (patientDrugId != null) {
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientDrugService.delete(Integer.valueOf(patientDrugId));
                session.setAttribute(Parameters.PATIENT_DRUGS_LIST, patientDrugService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/showpatient";
        } catch (DAOException e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/delpatientmedprocedure", method = RequestMethod.POST)
    public String delPatientMedProcedure(@RequestParam(value = Parameters.PATIENT_MEDPROCEDURE_ID, required = false) String patientMedProcedureId, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            if (patientMedProcedureId != null) {
                Patient patient = patientService.getById(Integer.parseInt((String) session.getAttribute(Parameters.PATIENT_ID)));
                patientMedProcedureService.delete(Integer.valueOf(patientMedProcedureId));
                session.setAttribute(Parameters.PATIENT_MEDPROCEDURES_LIST, patientMedProcedureService.getByPatient(patient));
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.SUCCESS_OPERATION, null, locale));
            } else {
                redirectAttributes.addFlashAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage(MessageConstants.EMPTY_CHOICE, null, locale));
            }
            return "redirect:/showpatient";
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


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String showErrorPage() {
        return pagePathManager.getProperty(ConfigConstants.ERROR_PAGE_PATH);
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