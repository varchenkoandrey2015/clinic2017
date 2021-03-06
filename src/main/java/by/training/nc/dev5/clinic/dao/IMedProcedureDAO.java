package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.MedProcedure;

import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
/**
 * Created by user on 24.04.2017.
 */
public interface IMedProcedureDAO {
    void add(MedProcedure medProcedure)throws DAOException;
    void delete(int id)throws DAOException;
    void update(MedProcedure entity) throws DAOException;
    MedProcedure getById(int id)throws DAOException;
//    List<MedProcedure> getByPatient(Patient patient)throws DAOException;
    List<MedProcedure> getAll()throws DAOException;
}
