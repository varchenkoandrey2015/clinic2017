package by.training.nc.dev5.clinic.entities;

import by.training.nc.dev5.clinic.entities.AbstractPrescribing;
import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Drug.getByPatient", query = "SELECT a FROM Drug a JOIN a.patients b WHERE b.id=?1"),
        @NamedQuery(name = "Drug.findAll", query = "SELECT o FROM Drug o")})

@Entity
public class Drug extends AbstractPrescribing implements Serializable {

    private List<Patient> patients;

    @ManyToMany
    @JoinTable(name = "patient_drug",
            joinColumns = @JoinColumn(name = "drugId"),
            inverseJoinColumns = @JoinColumn(name = "patientId"))
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
