/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pwp3villanuevag.service;


import com.mycompany.pwp3villanuevag.dao.PatientDao;
import com.mycompany.pwp3villanuevag.dao.RoomDao;
import com.mycompany.pwp3villanuevag.dto.RoomDTO;
import com.mycompany.pwp3villanuevag.exception.HospitalException;
import com.mycompany.pwp3villanuevag.model.ActionLog;
import com.mycompany.pwp3villanuevag.model.Patient;
import com.mycompany.pwp3villanuevag.model.Room;
import jakarta.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author villa
 */
public class HospitalService {
    private RoomDao roomdao;
    private PatientDao patientdao;
    
    public HospitalService() {
        roomdao = RoomDao.getInstance();
        patientdao = PatientDao.getInstance();
    }
    
    public void insertRoom(Room r) throws HospitalException {
        if(roomdao.getRoomById(r.getId()) != null){
            throw new HospitalException("Ja existeix una habitació amb el numero " + r.getId());
        }
        roomdao.saveRoom(r);
    }
    
    public List<Room> getRooms() {
        return roomdao.getAllRooms();
    }
    
    public void insertPatient(Patient p) throws HospitalException {
        if(patientdao.getPatientByNif(p.getNif()) != null){
            throw new HospitalException("Ja existeix un pacient amb el NIF " + p.getNif());
        } else if (!validateNif(p.getNif())){
            throw new HospitalException("El NIF " + p.getNif() + "no es valid.");
        }
        patientdao.savePatient(p);
    }
    private boolean validateNif(String nif) {
        Pattern REGEXP = Pattern.compile("[0-9]{8}[A-Z]");
        String DIGITO_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
        String[] INVALIDOS = new String[]{"00000000T", "00000001R", "99999999R"};
        return Arrays.binarySearch(INVALIDOS, nif) < 0 // <1>
                && REGEXP.matcher(nif).matches() // <2>
                && nif.charAt(8) == DIGITO_CONTROL.charAt(Integer.parseInt(nif.substring(0, 8)) % 23);
    }
    
    public List<Patient> getPatients() {
        return patientdao.getAllPatients();
    }
    public List<Patient> getPatientsLLiures() {
        return patientdao.getPatientsLliures();
    }
    public List<Patient> getPatientsOcupats() {
        return patientdao.getPatientsOcupats();
    }
    
    public List<RoomDTO> llistarEstatRooms() {
        System.out.println(roomdao.roomsState().get(1));
        return roomdao.roomsState();


    }
    
    public Room assignarHabitacio(String nif) throws HospitalException {
        List<Room> rooms;
        if(patientdao.getPatientByNif(nif).getMonitored() == true){
            rooms = roomdao.getEmptyMonitoredRooms();
            if(rooms.isEmpty()){
                throw new HospitalException("No hi ha habitacions lliures amb monitorització disponible");
            }
            patientdao.assignarRoom(nif, rooms.get(0));
        } else{
            rooms= roomdao.getEmptyRooms();
            if(rooms.isEmpty()){
                rooms=roomdao.getEmptyMonitoredRooms();
                if(rooms.isEmpty()){
                    throw new HospitalException("No hi ha cap habitació disponible ara mateix");
                }
                patientdao.assignarRoom(nif, rooms.get(0));
            }
            patientdao.assignarRoom(nif, rooms.get(0));
        }
        System.out.println(rooms.get(0));
        return rooms.get(0);
    }
    public void donarAlta(String nif) throws HospitalException {
        patientdao.donarAlta(nif);
    }
    public List<ActionLog> getHistorial(String nif) throws HospitalException {
        List<ActionLog> logs;
        if(!validateNif(nif)){
            throw new HospitalException("El NIF no es vàlid");
        } else{
            logs = patientdao.getPatientLog(nif);
        }
        
        return logs;
    }
    /*
     public void createTask(String userNif, Task t) throws TodoException, PersistenceException {
        User user = userdao.getUserByNif(userNif);
        if (user == null) {
            throw new TodoException("L'usuari amb NIF " + userNif + " no existeix.");
        }
        t.setUser(user);
        t.setCompleted(false);
        taskdao.save(t);
    }
     public List<Task> llistarTasquesPerUsuari(String nif) throws TodoException {
        User user = userdao.getUserByNif(nif);
        if (user == null) {
            throw new TodoException("L'usuari amb NIF " + nif + " no existeix.");
        }
        return taskdao.findByUserNif(nif);
    }
    public void marcarTascaCompletada(Integer id) throws PersistenceException {
        taskdao.marcarCompletada(id);
    }

    public void eliminarTasca(Integer id) throws PersistenceException {
        taskdao.eliminar(id);
    }
    public List<TaskDTO> llistarTasquesPendents() {
        return taskdao.findPendents();
    }
     */       
}
