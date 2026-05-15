/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author villa
 */
@Entity
@Table(name = "patient")
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findByNif", query = "SELECT p FROM Patient p WHERE p.nif = :nif"),
    @NamedQuery(name = "Patient.findByFullName", query = "SELECT p FROM Patient p WHERE p.fullName = :fullName"),
    @NamedQuery(name = "Patient.findByAge", query = "SELECT p FROM Patient p WHERE p.age = :age"),
    @NamedQuery(name = "Patient.findByDiagnosis", query = "SELECT p FROM Patient p WHERE p.diagnosis = :diagnosis"),
    @NamedQuery(name = "Patient.findByMonitored", query = "SELECT p FROM Patient p WHERE p.monitored = :monitored"),
    @NamedQuery(name = "Patient.findByUrgencyLevel", query = "SELECT p FROM Patient p WHERE p.urgencyLevel = :urgencyLevel")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nif")
    private String nif;
    @Basic(optional = false)
    @Column(name = "full_name")
    private String fullName;
    @Basic(optional = false)
    @Column(name = "age")
    private int age;
    @Basic(optional = false)
    @Column(name = "diagnosis")
    private String diagnosis;
    @Basic(optional = false)
    @Column(name = "monitored")
    private boolean monitored;
    @Basic(optional = false)
    @Column(name = "urgency_level")
    private int urgencyLevel;
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne
    private Room roomId;

    public Patient() {
    }

    public Patient(String nif) {
        this.nif = nif;
    }

    public Patient(String nif, String fullName, int age, String diagnosis, boolean monitored, int urgencyLevel) {
        this.nif = nif;
        this.fullName = fullName;
        this.age = age;
        this.diagnosis = diagnosis;
        this.monitored = monitored;
        this.urgencyLevel = urgencyLevel;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public boolean getMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nif != null ? nif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pwp3villanuevag.model.Patient[ nif=" + nif + " ]";
    }
    
}
