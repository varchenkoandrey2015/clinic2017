package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IPatientDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.dao.impl.PatientMySQLDAO;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.exceptions.NotFoundException;
import by.training.nc.dev5.clinic.services.AbstractService;
import by.training.nc.dev5.clinic.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class PatientService extends AbstractService<Patient>  implements IPatientService{

    private IPatientDAO patientDAO;

    @Autowired
    public PatientService(IPatientDAO patientDAO){
        super(patientDAO);
        this.patientDAO=patientDAO;
    }

    public List<Patient> getAll()throws DAOException {
        return patientDAO.getAll();
    }

    public boolean isNewPatient(String name)throws DAOException{
        try {
            patientDAO.getByName(name);
            return false;
        } catch (NotFoundException e){
            return true;
        }
    }

    public void add(Patient patient)throws DAOException{
        patientDAO.add(patient);
    }

    public Patient getById(int id)throws DAOException {
        return patientDAO.getById(id);
    }

    public void delete(int id)throws DAOException{
        patientDAO.delete(id);
    }
}
