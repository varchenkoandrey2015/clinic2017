package by.training.nc.dev5.clinic.entities;

import by.training.nc.dev5.clinic.entities.prescribings.Diagnosis;
import by.training.nc.dev5.clinic.entities.prescribings.Drug;
import by.training.nc.dev5.clinic.entities.prescribings.MedProcedure;
import by.training.nc.dev5.clinic.entities.prescribings.Surgery;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQueries({@NamedQuery(name = "Patient.findAll", query = "SELECT o FROM Patient o"),
               @NamedQuery(name = "Patient.getByName", query = "SELECT a FROM Patient a WHERE a.name=?1")})
/**
 * Created by user on 25.04.2017.
 */
@Entity
public class Patient extends AbstractEntity implements Serializable {

    private String name;
    private List<Diagnosis> diagnosises;
    private List<Drug> drugs;
    private List<MedProcedure> medProcedures;
    private List<Surgery> surgeries;


    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Diagnosis> getDiagnosises() {
        return diagnosises;
    }

    public void setDiagnosises(List<Diagnosis> diagnosises) {
        this.diagnosises = diagnosises;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<MedProcedure> getMedProcedures() {
        return medProcedures;
    }

    public void setMedProcedures(List<MedProcedure> medProcedures) {
        this.medProcedures = medProcedures;
    }

}
