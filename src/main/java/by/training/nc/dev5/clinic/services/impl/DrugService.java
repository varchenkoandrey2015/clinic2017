package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IDrugDAO;
import by.training.nc.dev5.clinic.entities.prescribings.Drug;
import by.training.nc.dev5.clinic.dao.impl.DrugMySQLDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.AbstractService;
import by.training.nc.dev5.clinic.services.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class DrugService extends AbstractService<Drug> implements IDrugService{

    private IDrugDAO drugDAO;

    @Autowired
    public DrugService(IDrugDAO drugDAO){
        super(drugDAO);
        this.drugDAO = drugDAO;
    }

    public List<Drug> getByPatient(Patient patient)throws DAOException {
        return drugDAO.getByPatient(patient);
    }

    public void delete(int id) throws DAOException{
        drugDAO.delete(id);
    }

    public void add(Drug drug)throws DAOException{
        drugDAO.add(drug);
    }

}
