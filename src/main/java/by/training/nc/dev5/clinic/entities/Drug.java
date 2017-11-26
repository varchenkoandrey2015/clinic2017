package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 25.04.2017.
 */
@NamedQueries({@NamedQuery(name = "Drug.findAll", query = "SELECT o FROM Drug o")})

@Entity
public class Drug implements Serializable {

    private Integer drugId;
    private String name;
    private String description;
    private List<PatientDrug> patientDrugs;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "drugId")
    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
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

    @OneToMany(mappedBy = "drug")
    public List<PatientDrug> getPatientDrugs() {
        return patientDrugs;
    }

    public void setPatientDrugs(List<PatientDrug> patientDrugs) {
        this.patientDrugs = patientDrugs;
    }
}
