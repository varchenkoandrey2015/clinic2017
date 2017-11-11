package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IMedProcedureDAO;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.dao.impl.MedProcedureMySQLDAO;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.AbstractService;
import by.training.nc.dev5.clinic.services.IMedProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class MedProcedureService extends AbstractService<MedProcedure> implements IMedProcedureService {

    private IMedProcedureDAO medProcedureDAO;

    @Autowired
    public MedProcedureService(IMedProcedureDAO medProcedureDAO){
        super(medProcedureDAO);
        this.medProcedureDAO=medProcedureDAO;
    }

    public List<MedProcedure> getByPatient(Patient patient)throws DAOException {
        return medProcedureDAO.getByPatient(patient);
    }

    public void delete(int id)throws DAOException{
        medProcedureDAO.delete(id);
    }

    public void add(MedProcedure medProcedure)throws DAOException{
        medProcedureDAO.add(medProcedure);
    }
}
