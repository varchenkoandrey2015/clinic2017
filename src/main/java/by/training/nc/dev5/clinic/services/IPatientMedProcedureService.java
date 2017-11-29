package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientMedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

public interface IPatientMedProcedureService {
    void add(PatientMedProcedure patientMedProcedure)throws DAOException;
    void delete(int id)throws DAOException;
    void update(PatientMedProcedure entity) throws DAOException;
    List<PatientMedProcedure> getByPatient(Patient patient) throws DAOException;
    PatientMedProcedure getById(int id) throws DAOException;
}
