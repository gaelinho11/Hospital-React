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
import com.mycompany.pwp3villanuevag.model.Patient;
import com.mycompany.pwp3villanuevag.model.Room;
import com.mycompany.pwp3villanuevag.service.HospitalService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author villa
 */
@WebServlet(name = "PatientServlet", urlPatterns = {"/api/patients/*"})
public class PatientServlet extends HttpServlet {

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
            Patient patient = gson.fromJson(request.getReader(), Patient.class);
            // 2. Cridar el service (valida duplicats + guarda)
            hospitalservice.insertPatient(patient);
            // 3. Retornar 201 Created amb l'objecte creat
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(patient));
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
        String a = request.getParameter("a");
        if (a != null && !a.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(hospitalservice.getPatientsOcupats()));
        }else{
            // GET /api/rooms → Retorna tots els usuaris
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(hospitalservice.getPatientsLLiures()));
        }
    }
    private String extractNif(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        try {
            return pathInfo.substring(1);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nif = extractNif(request);

        if (nif == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        } else {
            // PUT /api/tasks/{id} → Actualitza la tasca
            try {
                Room r = hospitalservice.assignarHabitacio(nif);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(r));
            } catch (PersistenceException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(gson.toJson("Error intern del servidor."));
            } catch (HospitalException ex) {

                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write(gson.toJson(ex.getMessage()));
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nif = extractNif(request);

        if (nif == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        } else{
            try {
                hospitalservice.donarAlta(nif);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (PersistenceException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(gson.toJson("Error intern del servidor."));
            } catch (HospitalException ex) {

                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write(gson.toJson(ex.getMessage()));
            }
        }
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "API REST de Patients";
    }// </editor-fold>

}
