/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author villa
 */
@Entity
@Table(name = "action_log")
@NamedQueries({
    @NamedQuery(name = "ActionLog.findAll", query = "SELECT a FROM ActionLog a"),
    @NamedQuery(name = "ActionLog.findById", query = "SELECT a FROM ActionLog a WHERE a.id = :id"),
    @NamedQuery(name = "ActionLog.findByTimestamp", query = "SELECT a FROM ActionLog a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "ActionLog.findByAction", query = "SELECT a FROM ActionLog a WHERE a.action = :action"),
    @NamedQuery(name = "ActionLog.findByDetail", query = "SELECT a FROM ActionLog a WHERE a.detail = :detail")})
public class ActionLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
    @Basic(optional = false)
    @Column(name = "action")
    private String action;
    @Column(name = "detail")
    private String detail;

    public ActionLog() {
    }

    public ActionLog(Integer id) {
        this.id = id;
    }

    public ActionLog(Integer id, LocalDateTime timestamp, String action) {
        this.id = id;
        this.timestamp = timestamp;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionLog)) {
            return false;
        }
        ActionLog other = (ActionLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pwp3villanuevag.model.ActionLog[ id=" + id + " ]";
    }
    
}
