package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.Diagnosis;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

/**
 * Created by user on 07.05.2017.
 */
public interface IDiagnosisService{
    void add(Diagnosis entity)throws DAOException;
    void delete(int id)throws DAOException;
    Diagnosis getById(int id)throws DAOException;
    void update(Diagnosis entity) throws DAOException;
    //    List<Diagnosis> getByPatient(Patient patient)throws DAOException;
    List<Diagnosis> getAll()throws DAOException;
}
