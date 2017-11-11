package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.dao.IDAO;
import by.training.nc.dev5.clinic.entities.AbstractEntity;
import by.training.nc.dev5.clinic.exceptions.DAOException;

/**
 * Created by user on 23.05.2017.
 */
public abstract class AbstractService <T extends AbstractEntity> implements IService<T>{
    private IDAO<T> dao;

    protected AbstractService(IDAO<T> dao){
        this.dao = dao;
    }

    public void add(T entity)throws DAOException {
        dao.add(entity);
    }

    public void delete(int id)throws DAOException{
        dao.delete(id);
    }
}
