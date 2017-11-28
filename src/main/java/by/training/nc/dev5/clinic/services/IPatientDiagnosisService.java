package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDiagnosis;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

public interface IPatientDiagnosisService {
    void add(PatientDiagnosis patientDiagnosis)throws DAOException;
    void delete(int id)throws DAOException;
    void update(PatientDiagnosis entity) throws DAOException;
    List<PatientDiagnosis> getByPatient(Patient patient) throws DAOException;
}
