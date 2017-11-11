package by.training.nc.dev5.clinic.dao.impl;

import by.training.nc.dev5.clinic.dao.AbstractDAO;
import by.training.nc.dev5.clinic.entities.Patient;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;
import by.training.nc.dev5.clinic.dao.ISurgeryDAO;
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
public class SurgeryMySQLDAO extends AbstractDAO<Surgery> implements ISurgeryDAO {

    private SurgeryMySQLDAO(){
        super(Surgery.class);
    }

    public List<Surgery> getByPatient(Patient patient)throws DAOException{
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            Query query = entityManager.createNamedQuery("Surgery.getByPatient");
            query.setParameter(1, patient);
            return (List<Surgery>) query.getResultList();
        } catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }

}
