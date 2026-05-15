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
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author villa
 */
@WebServlet(name = "LogServlet", urlPatterns = {"/api/logs/*"})
public class LogServlet extends HttpServlet {

    private HospitalService hospitalservice;
    private  Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
       hospitalservice = new HospitalService();
       gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, type, ctx) -> LocalDateTime.parse(json.getAsString()))
            .create();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String nif = request.getParameter("a");

        // GET /api/rooms → retorno el dto
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write(gson.toJson(hospitalservice.getHistorial(nif)));
        } catch (HospitalException ex) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(gson.toJson(ex.getMessage()));
        }

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
