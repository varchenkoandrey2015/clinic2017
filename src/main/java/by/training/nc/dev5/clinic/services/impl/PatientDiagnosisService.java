package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IPatientDiagnosisDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDiagnosis;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IPatientDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDiagnosisService implements IPatientDiagnosisService {
    private IPatientDiagnosisDAO patientDiagnosisDAO;

    @Autowired
    public PatientDiagnosisService(IPatientDiagnosisDAO iPatientDiagnosisDAO){
        this.patientDiagnosisDAO=iPatientDiagnosisDAO;
    }

    @Override
    public void add(PatientDiagnosis patientDiagnosis) throws DAOException {
        patientDiagnosisDAO.add(patientDiagnosis);
    }

    @Override
    public void update(PatientDiagnosis entity) throws DAOException {
        patientDiagnosisDAO.update(entity);
    }

    @Override
    public void delete(int id) throws DAOException {
        patientDiagnosisDAO.delete(id);
    }

    @Override
    public List<PatientDiagnosis> getByPatient(Patient patient) throws DAOException {
        return patientDiagnosisDAO.getByPatient(patient);
    }
}
