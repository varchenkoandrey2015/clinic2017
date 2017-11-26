package by.training.nc.dev5.clinic.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQueries({@NamedQuery(name = "Patient.findAll", query = "SELECT o FROM Patient o")})
/**
 * Created by user on 25.04.2017.
 */
@Entity
public class Patient implements Serializable {

    private Integer patientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private List<PatientDiagnosis> patientDiagnoses;
    private List<PatientDrug> patientDrugs;
    private List<PatientMedProcedure> patientMedProcedures;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "patientId")
    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @Column(name = "firstName", nullable = false)
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

    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(mappedBy = "patient")
    public List<PatientDiagnosis> getPatientDiagnoses() {
        return patientDiagnoses;
    }

    public void setPatientDiagnoses(List<PatientDiagnosis> patientDiagnoses) {
        this.patientDiagnoses = patientDiagnoses;
    }

    @OneToMany(mappedBy = "patient")
    public List<PatientDrug> getPatientDrugs() {
        return patientDrugs;
    }

    public void setPatientDrugs(List<PatientDrug> patientDrugs) {
        this.patientDrugs = patientDrugs;
    }

    @OneToMany(mappedBy = "patient")
    public List<PatientMedProcedure> getPatientMedProcedures() {
        return patientMedProcedures;
    }

    public void setPatientMedProcedures(List<PatientMedProcedure> patientMedProcedures) {
        this.patientMedProcedures = patientMedProcedures;
    }
}
