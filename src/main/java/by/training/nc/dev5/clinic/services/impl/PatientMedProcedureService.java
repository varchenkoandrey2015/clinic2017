package by.training.nc.dev5.clinic.services.impl;
import by.training.nc.dev5.clinic.dao.IPatientMedProcedureDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientMedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.services.IPatientMedProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMedProcedureService implements IPatientMedProcedureService{
    private IPatientMedProcedureDAO patientMedProcedureDAO;

    @Autowired
    public PatientMedProcedureService(IPatientMedProcedureDAO iPatientMedProcedureDAO){
        this.patientMedProcedureDAO=iPatientMedProcedureDAO;
    }

    @Override
    public void add(PatientMedProcedure patientMedProcedure) throws DAOException {
        patientMedProcedureDAO.add(patientMedProcedure);
    }

    @Override
    public void update(PatientMedProcedure entity) throws DAOException {
        patientMedProcedureDAO.update(entity);
    }

    @Override
    public void delete(int id) throws DAOException {
        patientMedProcedureDAO.delete(id);
    }

    @Override
    public List<PatientMedProcedure> getByPatient(Patient patient) throws DAOException {
        return patientMedProcedureDAO.getByPatient(patient);
    }
}
