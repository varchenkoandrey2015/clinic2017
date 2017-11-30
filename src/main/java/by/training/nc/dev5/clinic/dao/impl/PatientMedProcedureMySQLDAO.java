package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.IPatientMedProcedureDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientMedProcedure;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientMedProcedureMySQLDAO implements IPatientMedProcedureDAO{
    public void add(PatientMedProcedure entity) throws DAOException {
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

    public void update(PatientMedProcedure entity) throws DAOException {
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
            PatientMedProcedure entity = entityManager.find(PatientMedProcedure.class, id);
            entity.setPatient(null);
            entity.setMedProcedure(null);
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

    public List<PatientMedProcedure> getByPatient(Patient patient) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("PatientMedProcedure.getByPatient");
            query.setParameter(1, patient);
            return (List<PatientMedProcedure>) query.getResultList();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public PatientMedProcedure getById(int id) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(PatientMedProcedure.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }
}
