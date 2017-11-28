package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({@NamedQuery(name = "PatientDiagnosis.getByPatient", query = "SELECT a FROM PatientDiagnosis a WHERE a.patient=?1")})

@Entity
public class PatientDiagnosis implements Serializable {
    private Integer patientDiagnosisId;
    private Patient patient;
    private Diagnosis diagnosis;
    private String startDate;
    private String endDate;

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
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Column(name = "endDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
