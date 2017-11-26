package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.IPatientDAO;
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
public class PatientMySQLDAO  implements IPatientDAO {

    public void add(Patient entity) throws DAOException {
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
            Patient entity = entityManager.find(Patient.class, id);
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

    public List<Patient> getAll()throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("Patient.findAll");
            return (List<Patient>) query.getResultList();
        } catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }

    public Patient getById(int id)throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            return entityManager.find(Patient.class, id);
        }catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

//    public Patient getByName(String name)throws DAOException, NotFoundException{
//        List<Patient> patientList;
//        try {
//            EntityManager entityManager = HibernateUtil.getEntityManager();
//            Query query = entityManager.createNamedQuery("Patient.getByName");
//            query.setParameter(1, name);
//            patientList = (List<Patient>) query.getResultList();
//            return patientList.get(0);
//        } catch (IndexOutOfBoundsException e){
//            throw new NotFoundException();
//        }catch (Exception e){
//            throw new DAOException(e.getMessage());
//        }
//    }

}
