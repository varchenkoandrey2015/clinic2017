package by.training.nc.dev5.clinic.entities.prescribings;

import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Diagnosis.getByPatient", query = "SELECT a FROM Diagnosis a WHERE a.patient=?1")})

@Entity
public class Diagnosis extends AbstractPrescribing implements Serializable {

    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "PatientId")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
