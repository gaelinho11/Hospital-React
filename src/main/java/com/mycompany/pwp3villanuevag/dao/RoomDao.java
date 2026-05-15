/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.dao;

import com.mycompany.pwp3villanuevag.dto.RoomDTO;
import com.mycompany.pwp3villanuevag.model.ActionLog;
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
public class RoomDao {
    private EntityManagerFactory emf;
    private static RoomDao instance;
    
    public RoomDao(){
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }
    
    public static RoomDao getInstance() {
        if (instance == null) {
            instance = new RoomDao();
        }
        return instance;
    }
    
    public Room getRoomById(int id) {
        EntityManager em = emf.createEntityManager();
        Room r = em.find(Room.class, id); //solo se puede buscar por primary key con este metodo, devuelve null si no existe
        em.close();
        return r;
    }
    
    public void saveRoom(Room r) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(r);
            //afegeixo aqui el log perque crec que ho he de fer en la mateixa transacció com vam fer al Mail
            ActionLog log = new ActionLog();
            log.setTimestamp(LocalDateTime.now());
            log.setAction("ALTA_HAB");
            log.setDetail("Creada habitació " + r.getId() + " tipus " + r.getRoomType());
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
    
    public List<Room> getAllRooms() {
        EntityManager em = emf.createEntityManager();
        List<Room> rooms = em.createQuery("select r from Room r", Room.class).getResultList();
        em.close();
        return rooms;
    }
    public List<Room> getEmptyMonitoredRooms() { //con monitorizacion
        EntityManager em = emf.createEntityManager();
        List<Room> rooms = em.createQuery("select r from Room r where r.patientList is empty and r.monitored is true", Room.class).getResultList();
        em.close();
        return rooms;
    }
    public List<Room> getEmptyRooms() { //sin monitorizacion
        EntityManager em = emf.createEntityManager();
        List<Room> rooms = em.createQuery("select r from Room r where r.patientList is empty and r.monitored is false", Room.class).getResultList();
        em.close();
        return rooms;
    }
    public List<RoomDTO> roomsState() {
        EntityManager em = emf.createEntityManager();
        try {
            // join para poder crear el DTO acuerdate (me has dicho tu que esta bien :)
            return em.createQuery(
                "SELECT new com.mycompany.pwp3villanuevag.dto.RoomDTO(" +
                "r.id, r.roomType, r.monitored, p) " + 
                "FROM Room r LEFT JOIN r.patientList p", RoomDTO.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
}
