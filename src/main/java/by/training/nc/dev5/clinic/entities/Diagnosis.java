package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Diagnosis.findAll", query = "SELECT o FROM Diagnosis o")})

@Entity
public class Diagnosis implements Serializable {

    private Integer diagnosisId;
    private String name;
    private String description;
    private List<PatientDiagnosis> patientDiagnoses;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "diagnosisId")
    public Integer getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Integer diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "diagnosis")
    public List<PatientDiagnosis> getPatientDiagnoses() {
        return patientDiagnoses;
    }

    public void setPatientDiagnoses(List<PatientDiagnosis> patientDiagnoses) {
        this.patientDiagnoses = patientDiagnoses;
    }
}
