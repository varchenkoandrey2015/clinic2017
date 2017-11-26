package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PatientDiagnosis implements Serializable {
    private Integer patientDiagnosisId;
    private Patient patient;
    private Diagnosis diagnosis;
    private Date startDate;
    private Date endDate;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "patientDiagnosisId")
    public Integer getPatientDiagnosisId() {
        return patientDiagnosisId;
    }

    public void setPatientDiagnosisId(Integer patientDiagnosisId) {
        this.patientDiagnosisId = patientDiagnosisId;
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
    @JoinColumn(name = "diagnosisId")
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }


    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
