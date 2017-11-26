package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PatientDrug implements Serializable {
    private Integer patientDrugId;
    private Patient patient;
    private Drug drug;
    private Date startDate;
    private Integer days;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "patientDrugId")
    public Integer getPatientDrugId() {
        return patientDrugId;
    }

    public void setPatientDrugId(Integer patientDrugId) {
        this.patientDrugId = patientDrugId;
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
    @JoinColumn(name = "drugId")
    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "days")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
