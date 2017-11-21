package by.training.nc.dev5.clinic.entities;

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

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private List<Diagnosis> diagnosises;
    private List<Drug> drugs;
    private List<MedProcedure> medProcedures;

    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "diagnosisId"))
    public List<Diagnosis> getDiagnosises() {
        return diagnosises;
    }

    public void setDiagnosises(List<Diagnosis> diagnosises) {
        this.diagnosises = diagnosises;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patient_drug",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "drugId"))
    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patient_medprocedure",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "medprocedureId"))
    public List<MedProcedure> getMedProcedures() {
        return medProcedures;
    }

    public void setMedProcedures(List<MedProcedure> medProcedures) {
        this.medProcedures = medProcedures;
    }

}
