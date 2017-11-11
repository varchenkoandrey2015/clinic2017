package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.AbstractEntity;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.utils.HibernateUtil;

import javax.persistence.EntityManager;

/**
 * Created by user on 07.05.2017.
 */
public abstract class AbstractDAO <T extends AbstractEntity> implements IDAO<T>{
    private Class persistentClass;

    protected AbstractDAO(Class persistentClass){
        this.persistentClass = persistentClass;
    }

    public void add(T entity) throws DAOException {
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
            T entity = (T)entityManager.find(persistentClass, id);
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
}
