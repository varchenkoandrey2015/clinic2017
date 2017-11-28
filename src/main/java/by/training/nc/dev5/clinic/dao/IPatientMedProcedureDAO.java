package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientMedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

public interface IPatientMedProcedureDAO {
    void add(PatientMedProcedure patientMedProcedure)throws DAOException;
    void delete(int id)throws DAOException;
    void update(PatientMedProcedure entity) throws DAOException;
    List<PatientMedProcedure> getByPatient(Patient patient) throws DAOException;
}
