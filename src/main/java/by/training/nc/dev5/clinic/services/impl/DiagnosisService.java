package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.dao.IDiagnosisDAO;
import by.training.nc.dev5.clinic.entities.Diagnosis;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 22.04.2017.
 */
@Service("diagS")
public class DiagnosisService implements IDiagnosisService {
    private IDiagnosisDAO diagnosisDAO;

    @Autowired
    public DiagnosisService(IDiagnosisDAO diagnosisDAO){
        this.diagnosisDAO=diagnosisDAO;
    }

//    public List<Diagnosis> getByPatient(Patient patient)throws DAOException {
//        return diagnosisDAO.getByPatient(patient);
//    }

    public void add(Diagnosis diagnosis)throws DAOException{
        diagnosisDAO.add(diagnosis);
    }

    @Override
    public void update(Diagnosis entity) throws DAOException {
        diagnosisDAO.update(entity);
    }

    @Override
    public Diagnosis getById(int id) throws DAOException {
        return diagnosisDAO.getById(id);
    }

    public void delete(int id)throws DAOException{
        diagnosisDAO.delete(id);
    }

    @Override
    public List<Diagnosis> getAll() throws DAOException {
        return diagnosisDAO.getAll();
    }
}
