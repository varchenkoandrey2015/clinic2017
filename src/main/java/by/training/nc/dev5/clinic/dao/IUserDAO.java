package by.training.nc.dev5.clinic.dao;

import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.exceptions.*;

/**
 * Created by user on 06.04.2017.
 */
public interface IUserDAO extends IDAO<User>{
    User getByLogin(String login)throws DAOException, NotFoundException;
}
