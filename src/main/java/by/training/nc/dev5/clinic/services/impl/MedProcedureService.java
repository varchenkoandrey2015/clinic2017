package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IMedProcedureDAO;
import by.training.nc.dev5.clinic.entities.MedProcedure;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IMedProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service("procS")
public class MedProcedureService implements IMedProcedureService {

    private IMedProcedureDAO medProcedureDAO;

    @Autowired
    public MedProcedureService(IMedProcedureDAO medProcedureDAO){
        this.medProcedureDAO=medProcedureDAO;
    }


//    public List<MedProcedure> getByPatient(Patient patient)throws DAOException {
//        return medProcedureDAO.getByPatient(patient);
//    }

    public void delete(int id)throws DAOException{
        medProcedureDAO.delete(id);
    }

    public void add(MedProcedure medProcedure)throws DAOException{
        medProcedureDAO.add(medProcedure);
    }

    @Override
    public void update(MedProcedure entity) throws DAOException {
        medProcedureDAO.update(entity);
    }

    @Override
    public MedProcedure getById(int id) throws DAOException {
        return medProcedureDAO.getById(id);
    }

    @Override
    public List<MedProcedure> getAll() throws DAOException {
        return medProcedureDAO.getAll();
    }
}
