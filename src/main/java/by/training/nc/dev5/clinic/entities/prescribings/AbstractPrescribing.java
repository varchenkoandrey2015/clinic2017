package by.training.nc.dev5.clinic.entities.prescribings;

import by.training.nc.dev5.clinic.entities.AbstractEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by user on 03.05.2017.
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractPrescribing extends AbstractEntity {
    private String name;

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
