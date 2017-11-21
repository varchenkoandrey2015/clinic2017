package by.training.nc.dev5.clinic.entities;

import by.training.nc.dev5.clinic.entities.AbstractPrescribing;
import by.training.nc.dev5.clinic.entities.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "MedProcedure.getByPatient", query = "SELECT a FROM MedProcedure a JOIN a.patients b WHERE b.id=?11"),
        @NamedQuery(name = "MedProcedure.findAll", query = "SELECT o FROM MedProcedure o")})

@Entity
public class MedProcedure extends AbstractPrescribing implements Serializable {

    private List<Patient> patients;

    @ManyToMany
    @JoinTable(name = "patient_medprocedure",
            joinColumns = @JoinColumn(name = "medprocedureId"),
            inverseJoinColumns = @JoinColumn(name = "patientId"))
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
