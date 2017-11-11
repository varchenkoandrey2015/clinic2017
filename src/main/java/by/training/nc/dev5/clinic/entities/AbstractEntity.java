package by.training.nc.dev5.clinic.entities;

/**
 * Created by user on 02.05.2017.
 */

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractEntity {

    protected Integer id;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}
