package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.ISurgeryDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.dao.impl.SurgeryMySQLDAO;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.AbstractService;
import by.training.nc.dev5.clinic.services.ISurgeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class SurgeryService extends AbstractService<Surgery>  implements ISurgeryService{

    private ISurgeryDAO surgeryDAO;

    @Autowired
    public SurgeryService(ISurgeryDAO surgeryDAO){
        super(surgeryDAO);
        this.surgeryDAO=surgeryDAO;
    }

    public List<Surgery> getByPatient(Patient patient)throws DAOException {
        return surgeryDAO.getByPatient(patient);
    }

    public void add(Surgery surgery)throws DAOException{
        surgeryDAO.add(surgery);
    }

    public void delete(int id)throws DAOException{
        surgeryDAO.delete(id);
    }
}
