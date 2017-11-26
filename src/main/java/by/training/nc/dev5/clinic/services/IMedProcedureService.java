package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.MedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

/**
 * Created by user on 07.05.2017.
 */
public interface IMedProcedureService{
    void add(MedProcedure entity)throws DAOException;
    void delete(int id)throws DAOException;
    void update(MedProcedure entity) throws DAOException;
    MedProcedure getById(int id)throws DAOException;
//    List<MedProcedure> getByPatient(Patient patient)throws DAOException;
    List<MedProcedure> getAll()throws DAOException;
}
