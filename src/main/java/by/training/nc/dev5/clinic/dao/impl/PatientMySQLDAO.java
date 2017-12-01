package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.IPatientDAO;
import by.training.nc.dev5.clinic.dao.IPatientDiagnosisDAO;
import by.training.nc.dev5.clinic.dao.IPatientDrugDAO;
import by.training.nc.dev5.clinic.dao.IPatientMedProcedureDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDiagnosis;
import by.training.nc.dev5.clinic.entities.PatientDrug;
import by.training.nc.dev5.clinic.entities.PatientMedProcedure;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import by.training.nc.dev5.clinic.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by user on 06.04.2017.
 */
@Repository
public class PatientMySQLDAO implements IPatientDAO {
    private IPatientDiagnosisDAO patientDiagnosisDAO;
    private IPatientDrugDAO patientDrugDAO;
    private IPatientMedProcedureDAO patientMedProcedureDAO;

    @Autowired
    public PatientMySQLDAO(IPatientDiagnosisDAO iPatientDiagnosisDAO, IPatientDrugDAO iPatientDrugDAO,
                           IPatientMedProcedureDAO iPatientMedProcedureDAO) {
        this.patientDiagnosisDAO = iPatientDiagnosisDAO;
        this.patientDrugDAO = iPatientDrugDAO;
        this.patientMedProcedureDAO = iPatientMedProcedureDAO;
    }

    public void add(Patient entity) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        } finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public void update(Patient entity) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        } finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public void delete(int id) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            Patient entity = entityManager.find(Patient.class, id);
            for(PatientDiagnosis patientDiagnosis:entity.getPatientDiagnoses()){
                patientDiagnosisDAO.delete(patientDiagnosis.getPatientDiagnosisId());
            }
            for(PatientDrug patientDrug:entity.getPatientDrugs()){
                patientDrugDAO.delete(patientDrug.getPatientDrugId());
            }
            for(PatientMedProcedure patientMedProcedure:entity.getPatientMedProcedures()){
                patientMedProcedureDAO.delete(patientMedProcedure.getPatientMedProcedureId());
            }
            entity.setPatientDiagnoses(null);
            entity.setPatientDrugs(null);
            entity.setPatientMedProcedures(null);
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        } finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public List<Patient> getAll() throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("Patient.findAll");
            return (List<Patient>) query.getResultList();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Patient getById(int id) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(Patient.class, id);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

}
