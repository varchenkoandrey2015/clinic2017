package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IPatientDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.exceptions.NotFoundException;
import by.training.nc.dev5.clinic.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class PatientService  implements IPatientService{

    private IPatientDAO patientDAO;

    @Autowired
    public PatientService(IPatientDAO patientDAO){
        this.patientDAO=patientDAO;
    }

    public List<Patient> getAll()throws DAOException {
        return patientDAO.getAll();
    }

    public void add(Patient patient)throws DAOException{
        patientDAO.add(patient);
    }

    @Override
    public void update(Patient entity) throws DAOException {
        patientDAO.update(entity);
    }

    public Patient getById(int id)throws DAOException {
        return patientDAO.getById(id);
    }

    public void delete(int id)throws DAOException{
        patientDAO.delete(id);
    }
}
