/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.pwp3villanuevag.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.mycompany.pwp3villanuevag.exception.HospitalException;
import com.mycompany.pwp3villanuevag.model.Room;
import com.mycompany.pwp3villanuevag.service.HospitalService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author villa
 */
@WebServlet(name = "RoomServlet", urlPatterns = {"/api/rooms/*"})
public class RoomServlet extends HttpServlet {

    private HospitalService hospitalservice;
    private  Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
       hospitalservice = new HospitalService();
       gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    // Exclou el camp 'user' de Task i 'taskList' de User
                    return f.getName().equals("patientList") || f.getName().equals("roomId");
                }
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            // 1. Llegir el body JSON i convertir-lo a objecte User
            Room room = gson.fromJson(request.getReader(), Room.class);
            // 2. Cridar el service (valida duplicats + guarda)
            hospitalservice.insertRoom(room);
            // 3. Retornar 201 Created amb l'objecte creat
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(room));
        } catch (HospitalException ex) {
            // Error de negoci: usuari duplicat → 409 Conflict
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(gson.toJson(ex.getMessage()));
        } catch (PersistenceException ex) {
            // Error de BBDD inesperat → 500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error intern del servidor: " + ex.getMessage()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // GET /api/rooms → retorno el dto
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(hospitalservice.llistarEstatRooms()));

    }
    
    
    
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "API REST de Rooms";
    }// </editor-fold>

}
