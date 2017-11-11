package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.prescribings.Diagnosis;
import by.training.nc.dev5.clinic.entities.Patient;

import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
/**
 * Created by user on 24.04.2017.
 */
public interface IDiagnosisDAO extends IDAO<Diagnosis> {
    List<Diagnosis> getByPatient(Patient patient)throws DAOException;
}
