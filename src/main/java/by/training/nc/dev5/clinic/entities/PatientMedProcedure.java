package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({@NamedQuery(name = "PatientMedProcedure.getByPatient", query = "SELECT a FROM PatientMedProcedure a WHERE a.patient=?1")})

@Entity
public class PatientMedProcedure implements Serializable {
    private Integer patientMedProcedureId;
    private Patient patient;
    private MedProcedure medProcedure;
    private String startDate;
    private Integer count;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "patientMedProcedureId")
    public Integer getPatientMedProcedureId() {
        return patientMedProcedureId;
    }

    public void setPatientMedProcedureId(Integer patientMedProcedureId) {
        this.patientMedProcedureId = patientMedProcedureId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patientId")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medProcedureId")
    public MedProcedure getMedProcedure() {
        return medProcedure;
    }

    public void setMedProcedure(MedProcedure medProcedure) {
        this.medProcedure = medProcedure;
    }

    @Column(name = "startDate", nullable = false)
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
