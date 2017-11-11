package by.training.nc.dev5.clinic.exceptions;

/**
 * Created by user on 30.04.2017.
 */
public class DAOException extends Exception {
    public DAOException() {}

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
