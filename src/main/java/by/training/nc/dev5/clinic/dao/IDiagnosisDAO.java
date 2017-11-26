package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Diagnosis;
import by.training.nc.dev5.clinic.entities.Patient;

import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
/**
 * Created by user on 24.04.2017.
 */
public interface IDiagnosisDAO {
    void add(Diagnosis diagnosis)throws DAOException;
    void delete(int id)throws DAOException;
    Diagnosis getById(int id)throws DAOException;
    void update(Diagnosis entity) throws DAOException;
    //    List<Diagnosis> getByPatient(Patient patient)throws DAOException;
    List<Diagnosis> getAll()throws DAOException;
}
