package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "MedProcedure.findAll", query = "SELECT o FROM MedProcedure o")})

@Entity
public class MedProcedure implements Serializable {

    private Integer medProcedureId;
    private String name;
    private String description;
    private List<PatientMedProcedure> patientMedProcedures;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "medProcedureId")
    public Integer getMedProcedureId() {
        return medProcedureId;
    }

    public void setMedProcedureId(Integer medProcedureId) {
        this.medProcedureId = medProcedureId;
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

    @OneToMany(mappedBy = "medProcedure")
    public List<PatientMedProcedure> getPatientMedProcedures() {
        return patientMedProcedures;
    }

    public void setPatientMedProcedures(List<PatientMedProcedure> patientMedProcedures) {
        this.patientMedProcedures = patientMedProcedures;
    }
}
