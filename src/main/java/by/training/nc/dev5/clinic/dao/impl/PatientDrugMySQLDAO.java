package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.IPatientDrugDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.PatientDrug;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientDrugMySQLDAO implements IPatientDrugDAO{
    public void add(PatientDrug entity) throws DAOException {
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

    public void update(PatientDrug entity) throws DAOException {
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
            PatientDrug entity = entityManager.find(PatientDrug.class, id);
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

    public List<PatientDrug> getByPatient(Patient patient) throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("PatientDrug.getByPatient");
            query.setParameter(1, patient);
            return (List<PatientDrug>) query.getResultList();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

}
