/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.dao;

import com.mycompany.pwp3villanuevag.exception.HospitalException;
import com.mycompany.pwp3villanuevag.model.ActionLog;
import com.mycompany.pwp3villanuevag.model.Patient;
import com.mycompany.pwp3villanuevag.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author villa
 */
public class PatientDao {
    private EntityManagerFactory emf;
    private static PatientDao instance;
    
    public PatientDao(){
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }
    
    public static PatientDao getInstance() {
        if (instance == null) {
            instance = new PatientDao();
        }
        return instance;
    }
    
    public Patient getPatientByNif(String nif) {
        EntityManager em = emf.createEntityManager();
        Patient p = em.find(Patient.class, nif); //solo se puede buscar por primary key con este metodo, devuelve null si no existe
        em.close();
        return p;
    }
    
    public void savePatient(Patient p) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(p);
            //afegeixo aqui el log perque crec que ho he de fer en la mateixa transacció com vam fer al Mail
            ActionLog log = new ActionLog();
            log.setTimestamp(LocalDateTime.now());
            log.setAction("ALTA_PAC");
            log.setDetail("Registrat pacient " + p.getNif() + " ( " + p.getFullName() + " )");
            em.persist(log);
            em.getTransaction().commit();
        } catch(PersistenceException ex) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw ex;
        } finally  {
            em.close();
        }
    }
    
    public List<Patient> getAllPatients() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patients = em.createQuery("select p from Patient p", Patient.class).getResultList();
        em.close();
        return patients;
    }
    public List<Patient> getPatientsLliures() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patients = em.createQuery("select p from Patient p where p.roomId is null", Patient.class).getResultList();
        em.close();
        return patients;
    }
    public List<Patient> getPatientsOcupats() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patients = em.createQuery("select p from Patient p where p.roomId.id is not null", Patient.class).getResultList();
        em.close();
        return patients;
    }
    
    public void assignarRoom(String nif, Room r) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Patient p = em.find(Patient.class, nif);
            if (p != null) {
                ActionLog log = new ActionLog();
                log.setTimestamp(LocalDateTime.now());
                log.setAction("INGR_PAC");
                log.setDetail("Pacient " + p.getNif() + " ( " + p.getFullName() + " ) assignat a  l'habitació " + p.getRoomId());

                em.persist(log);
                p.setRoomId(r);
            }
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
    public void donarAlta(String nif) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Patient p = em.find(Patient.class, nif);
            if (p != null) {
                ActionLog log = new ActionLog();
                log.setTimestamp(LocalDateTime.now());
                log.setAction("ALTA_MED");
                log.setDetail("Pacient " + p.getNif() + " ( " + p.getFullName() + " ) esborrat. Habitació " + p.getRoomId().getId() + " lliure.");
                em.persist(log);
                em.remove(p);
                em.getTransaction().commit();
            }
        } catch (PersistenceException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
    public List<ActionLog> getPatientLog(String nif) throws HospitalException{
        EntityManager em = emf.createEntityManager();
        List<ActionLog> logs = em.createQuery("SELECT l FROM ActionLog l WHERE l.detail LIKE '%" + nif + "%' ORDER BY l.timestamp DESC", ActionLog.class).getResultList();
        em.close();
        if(logs.isEmpty()){
            throw new HospitalException("No hi ha cap pacient amb aquest NIF");
        }
        return logs;
    }
    
}
