package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Drug;
import by.training.nc.dev5.clinic.entities.Patient;

import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
/**
 * Created by user on 24.04.2017.
 */
public interface IDrugDAO{
    void add(Drug drug)throws DAOException;
    void delete(int id)throws DAOException;
    void update(Drug entity) throws DAOException;
    Drug getById(int id)throws DAOException;
//    List<Drug> getByPatient(Patient patient)throws DAOException;
    List<Drug> getAll()throws DAOException;
}
