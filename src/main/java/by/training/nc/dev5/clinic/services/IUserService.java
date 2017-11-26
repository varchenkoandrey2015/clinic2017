package by.training.nc.dev5.clinic.services;

import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.exceptions.DAOException;

/**
 * Created by user on 07.05.2017.
 */
public interface IUserService{
    void add(User entity)throws DAOException;
    void delete(int id)throws DAOException;
    boolean isAuthorized(String login, String password)throws DAOException;
    User getByLogin(String login)throws DAOException;
    boolean isNewUser(String login) throws DAOException;
}
