package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IDrugDAO;
import by.training.nc.dev5.clinic.entities.Drug;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service("drugS")
public class DrugService implements IDrugService{

    private IDrugDAO drugDAO;

    @Autowired
    public DrugService(IDrugDAO drugDAO){
        this.drugDAO = drugDAO;
    }

//    public List<Drug> getByPatient(Patient patient)throws DAOException {
//        return drugDAO.getByPatient(patient);
   // }

    public void delete(int id) throws DAOException{
        drugDAO.delete(id);
    }

    public void add(Drug drug)throws DAOException{
        drugDAO.add(drug);
    }

    @Override
    public void update(Drug entity) throws DAOException {
        drugDAO.update(entity);
    }

    @Override
    public Drug getById(int id) throws DAOException {
        return drugDAO.getById(id);
    }

    @Override
    public List<Drug> getAll() throws DAOException {
        return drugDAO.getAll();
    }
}
