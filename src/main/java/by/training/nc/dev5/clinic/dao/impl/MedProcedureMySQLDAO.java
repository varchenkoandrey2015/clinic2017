package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.MedProcedure;
import by.training.nc.dev5.clinic.dao.IMedProcedureDAO;
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
public class MedProcedureMySQLDAO implements IMedProcedureDAO {

    public void add(MedProcedure entity) throws DAOException {
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

    public void update(MedProcedure entity) throws DAOException {
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
            MedProcedure entity = entityManager.find(MedProcedure.class, id);
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

    public MedProcedure getById(int id)throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(MedProcedure.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }
//    public List<MedProcedure> getByPatient(Patient patient)throws DAOException{
//        try {
//            EntityManager entityManager = HibernateUtil.getEntityManager();
//            Query query = entityManager.createNamedQuery("MedProcedure.getByPatient");
//            query.setParameter(1, patient.getPatientId());
//            return (List<MedProcedure>) query.getResultList();
//        } catch (Exception e){
//            throw new DAOException(e.getMessage());
//        }
//    }

    @Override
    public List<MedProcedure> getAll() throws DAOException {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("MedProcedure.findAll");
            return (List<MedProcedure>) query.getResultList();
        } catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }
}
