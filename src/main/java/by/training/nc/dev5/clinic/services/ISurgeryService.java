package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.exceptions.DAOException;

import java.util.List;

/**
 * Created by user on 07.05.2017.
 */
public interface ISurgeryService extends IService<Surgery>{
    List<Surgery> getByPatient(Patient patient)throws DAOException;
}
