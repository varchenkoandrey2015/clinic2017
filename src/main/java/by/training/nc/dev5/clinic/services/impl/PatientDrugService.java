package by.training.nc.dev5.clinic.services.impl;


import by.training.nc.dev5.clinic.dao.IPatientDrugDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDrug;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IPatientDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDrugService implements IPatientDrugService{
    private IPatientDrugDAO patientDrugDAO;

    @Autowired
    public PatientDrugService(IPatientDrugDAO iPatientDrugDAO){
        this.patientDrugDAO=iPatientDrugDAO;
    }

    @Override
    public void add(PatientDrug patientDrug) throws DAOException {
        patientDrugDAO.add(patientDrug);
    }

    @Override
    public void update(PatientDrug entity) throws DAOException {
        patientDrugDAO.update(entity);
    }

    @Override
    public void delete(int id) throws DAOException {
        patientDrugDAO.delete(id);
    }

    @Override
    public List<PatientDrug> getByPatient(Patient patient) throws DAOException {
        return patientDrugDAO.getByPatient(patient);
    }

    @Override
    public PatientDrug getById(int id) throws DAOException {
        return patientDrugDAO.getById(id);
    }
}
