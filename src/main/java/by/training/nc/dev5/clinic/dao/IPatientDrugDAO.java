package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDrug;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

public interface IPatientDrugDAO {
    void add(PatientDrug patientDrug)throws DAOException;
    void delete(int id)throws DAOException;
    void update(PatientDrug entity) throws DAOException;
    List<PatientDrug> getByPatient(Patient patient) throws DAOException;
    PatientDrug getById(int id) throws DAOException;
}
