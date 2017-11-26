package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.Drug;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

/**
 * Created by user on 07.05.2017.
 */
public interface IDrugService{
    void add(Drug entity)throws DAOException;
    void delete(int id)throws DAOException;
    void update(Drug entity) throws DAOException;
    Drug getById(int id)throws DAOException;
//    List<Drug> getByPatient(Patient patient)throws DAOException;
    List<Drug> getAll()throws DAOException;
}
