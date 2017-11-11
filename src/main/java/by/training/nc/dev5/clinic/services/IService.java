package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.AbstractEntity;
import by.training.nc.dev5.clinic.exceptions.DAOException;

/**
 * Created by user on 07.05.2017.
 */
public interface IService<T extends AbstractEntity> {
    void add(T entity)throws DAOException;
    void delete(int id)throws DAOException;
}
