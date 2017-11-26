package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;

/**
 * Created by user on 06.04.2017.
 */
public interface IPatientDAO{
    void add(Patient patient)throws DAOException;
    void delete(int id)throws DAOException;
    List<Patient> getAll() throws DAOException;
    Patient getById(int patientId)throws DAOException;
//    Patient getByName(String name)throws DAOException, NotFoundException;
}
