package by.training.nc.dev5.clinic.services.impl;

import by.training.nc.dev5.clinic.constants.AccessLevels;
import by.training.nc.dev5.clinic.dao.IUserDAO;
import by.training.nc.dev5.clinic.entities.User;
import by.training.nc.dev5.clinic.dao.impl.UserMySQLDAO;
import by.training.nc.dev5.clinic.exceptions.DAOException;
import by.training.nc.dev5.clinic.exceptions.NotFoundException;
import by.training.nc.dev5.clinic.enums.UserType;
import by.training.nc.dev5.clinic.services.AbstractService;
import by.training.nc.dev5.clinic.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 22.04.2017.
 */
@Service
public class UserService extends AbstractService<User>  implements IUserService{

    IUserDAO userDAO;

    @Autowired
    public UserService(IUserDAO userDAO){
        super(userDAO);
        this.userDAO=userDAO;
    }

    public boolean isAuthorized(String login, String password)throws DAOException {
        try {
            return (password.equals(userDAO.getByLogin(login).getPassword()));
        }
        catch (NotFoundException e){
            return false;
        }
    }

    public User getByLogin(String login)throws DAOException {
        try {
            return userDAO.getByLogin(login);
        }catch (NotFoundException e) {
            return null;
        }
    }

    public UserType checkAccessLevel(String login) throws DAOException{
        try{
            if(AccessLevels.DOCTOR.equals(userDAO.getByLogin(login).getAccessLevel())){
                return UserType.DOCTOR;
            }else{
                return UserType.NURSE;
            }
        } catch (NotFoundException e){
            return null;
        }
    }

    public void add(User user)throws DAOException {
        userDAO.add(user);
    }

    public boolean isNewUser(String login) throws DAOException {
        try {
            userDAO.getByLogin(login);
            return false;
        } catch (NotFoundException e){
            return true;
        }

    }

    public void delete(int id)throws DAOException{
        userDAO.delete(id);
    }
}
