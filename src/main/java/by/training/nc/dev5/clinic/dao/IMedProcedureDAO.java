package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;

import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
/**
 * Created by user on 24.04.2017.
 */
public interface IMedProcedureDAO extends IDAO<MedProcedure> {
    List<MedProcedure> getByPatient(Patient patient)throws DAOException;
}
