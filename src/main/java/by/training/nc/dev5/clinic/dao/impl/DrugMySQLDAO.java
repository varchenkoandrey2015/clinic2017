package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.entities.Drug;
import by.training.nc.dev5.clinic.dao.IDrugDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.utils.HibernateUtil;
import by.training.nc.dev5.clinic.exceptions.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by user on 06.04.2017.
 */
@Repository
public class  DrugMySQLDAO implements IDrugDAO {

    public void add(Drug entity) throws DAOException {
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

    public void delete(int id) throws DAOException{
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            Drug entity = entityManager.find(Drug.class, id);
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

    public void update(Drug entity) throws DAOException {
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

    public Drug getById(int id)throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(Drug.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

//    public List<Drug> getByPatient(Patient patient)throws DAOException{
//        try {
//            EntityManager entityManager = HibernateUtil.getEntityManager();
//            Query query = entityManager.createNamedQuery("Drug.getByPatient");
//            query.setParameter(1, patient.getPatientId());
//            return (List<Drug>) query.getResultList();
//        } catch (Exception e){
//            throw new DAOException(e.getMessage());
//        }
//    }

    @Override
    public List<Drug> getAll() throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("Drug.findAll");
            return (List<Drug>) query.getResultList();
        } catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }
}
