package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

/**
 * Created by user on 07.05.2017.
 */
public interface IPatientService{
    void add(Patient entity)throws DAOException;
    void delete(int id)throws DAOException;
    void update(Patient entity) throws DAOException;
    List<Patient> getAll()throws DAOException;
    Patient getById(int id)throws DAOException;
}
