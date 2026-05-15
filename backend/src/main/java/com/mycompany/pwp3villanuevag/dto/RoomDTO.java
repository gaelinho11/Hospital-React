/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.dto;

import com.mycompany.pwp3villanuevag.model.Patient;

/**
 *
 * @author villa
 */
public class RoomDTO {
    private Integer numero;
    private String tipus;
    private boolean monitoritzada;
    private boolean lliure;
    private Patient pacient;

    public RoomDTO(Integer numero, String tipus, boolean monitoritzada, Patient pacient) {
        this.numero = numero;
        this.tipus = tipus;
        this.monitoritzada = monitoritzada;
        if(pacient == null){ //la consulta que he fet em porta el pacient null si no te pacient assignat la room
            this.lliure = true;
        }else{
            this.lliure = false;
        }
        this.pacient = pacient;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public boolean isMonitoritzada() {
        return monitoritzada;
    }

    public void setMonitoritzada(boolean monitoritzada) {
        this.monitoritzada = monitoritzada;
    }

    public boolean isLliure() {
        return lliure;
    }

    public void setLliure(boolean lliure) {
        this.lliure = lliure;
    }

    public Patient getPacient() {
        return pacient;
    }

    public void setPacient(Patient pacient) {
        this.pacient = pacient;
    }
    
    
    
}
