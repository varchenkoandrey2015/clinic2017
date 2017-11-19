package by.training.nc.dev5.clinic.entities.prescribings;

import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Surgery.getByPatient", query = "SELECT a FROM Surgery a JOIN a.patients b WHERE b.id=?1"),
        @NamedQuery(name = "Surgery.findAll", query = "SELECT o FROM Surgery o")})

@Entity
public class Surgery extends AbstractPrescribing implements Serializable {

    private List<Patient> patients;

    @ManyToMany
    @JoinTable(name = "patient_surgery",
            joinColumns = @JoinColumn(name = "surgery_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
