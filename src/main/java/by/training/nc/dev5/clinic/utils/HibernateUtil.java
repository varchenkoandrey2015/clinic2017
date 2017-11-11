package by.training.nc.dev5.clinic.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by user on 24.04.2017.
 */
public class HibernateUtil {
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AV-Clinic");
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    public static void closeEntityManager(){
        entityManager = null;
    }
}
