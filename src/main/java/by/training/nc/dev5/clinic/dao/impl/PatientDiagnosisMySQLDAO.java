package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.IPatientDiagnosisDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDiagnosis;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientDiagnosisMySQLDAO implements IPatientDiagnosisDAO {

    public void add(PatientDiagnosis entity) throws DAOException {
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

    public void update(PatientDiagnosis entity) throws DAOException {
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
            PatientDiagnosis entity = entityManager.find(PatientDiagnosis.class, id);
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

    public List<PatientDiagnosis> getByPatient(Patient patient) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("PatientDiagnosis.getByPatient");
            query.setParameter(1, patient);
            return (List<PatientDiagnosis>) query.getResultList();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public PatientDiagnosis getById(int id) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(PatientDiagnosis.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }
}
