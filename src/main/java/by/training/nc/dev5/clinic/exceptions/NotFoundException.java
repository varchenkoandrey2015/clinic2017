package by.training.nc.dev5.clinic.exceptions;

/**
 * Created by user on 30.04.2017.
 */
public class NotFoundException extends Exception {
    public NotFoundException() {}

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
