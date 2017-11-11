package by.training.nc.dev5.clinic.entities.prescribings;

import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "MedProcedure.getByPatient", query = "SELECT a FROM MedProcedure a WHERE a.patient=?1")})

@Entity
public class MedProcedure extends AbstractPrescribing implements Serializable {

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
