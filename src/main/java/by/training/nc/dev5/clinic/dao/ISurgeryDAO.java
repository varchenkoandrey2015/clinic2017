package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.exceptions.*;
import java.util.List;

/**
 * Created by user on 24.04.2017.
 */
public interface ISurgeryDAO extends IDAO<Surgery>{
    List<Surgery> getByPatient(Patient patient)throws DAOException;
}
