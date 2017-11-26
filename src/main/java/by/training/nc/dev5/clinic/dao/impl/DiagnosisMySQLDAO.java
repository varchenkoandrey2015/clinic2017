package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.entities.Diagnosis;
import by.training.nc.dev5.clinic.dao.IDiagnosisDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import by.training.nc.dev5.clinic.exceptions.*;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 06.04.2017.
 */
@Repository
public class  DiagnosisMySQLDAO implements IDiagnosisDAO {

    public void add(Diagnosis entity) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public void update(Diagnosis entity) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public void delete(int id) throws DAOException{
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            Diagnosis entity = entityManager.find(Diagnosis.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }finally {
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    public Diagnosis getById(int id)throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(Diagnosis.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

//    public List<Diagnosis> getByPatient(Patient patient)throws DAOException{
//        try {
//            EntityManager entityManager = HibernateUtil.getEntityManager();
//            Query query = entityManager.createNamedQuery("Diagnosis.getByPatient");
//            query.setParameter(1, patient.getPatientId());
//            return (List<Diagnosis>) query.getResultList();
//        } catch (Exception e){
//            throw new DAOException(e.getMessage());
//        }
//    }

    @Override
    public List<Diagnosis> getAll() throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("Diagnosis.findAll");
            return (List<Diagnosis>) query.getResultList();
        } catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }
}

