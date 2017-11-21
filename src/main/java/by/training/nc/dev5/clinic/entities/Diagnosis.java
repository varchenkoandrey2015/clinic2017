package by.training.nc.dev5.clinic.entities;

import by.training.nc.dev5.clinic.entities.AbstractPrescribing;
import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Diagnosis.getByPatient", query = "SELECT a FROM Diagnosis a JOIN a.patients b WHERE b.id=?1"),
                @NamedQuery(name = "Diagnosis.findAll", query = "SELECT o FROM Diagnosis o")})

@Entity
public class Diagnosis extends AbstractPrescribing implements Serializable {

    private List<Patient> patients;

    @ManyToMany
    @JoinTable(name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "diagnosisId"),
            inverseJoinColumns = @JoinColumn(name = "patientId"))
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
