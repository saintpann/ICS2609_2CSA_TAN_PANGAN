/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import miscs.Report;

/**
 *
 * @author Kimberly
 */
public class GenerateReportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int i = 0;
        Object currentUser[] = null;
        HttpSession htps = request.getSession(false);
        if (htps != null){
            currentUser = (Object[]) htps.getAttribute("paremeters");
        }
        else{
            htps.invalidate();
            response.sendRedirect("login");
            return;
        }
        try{
            Report rep = new Report();
            rep.generate((String) currentUser[0], (String) currentUser[1],(String) currentUser[2],  (List<String[]>) currentUser[3], (String) currentUser[4]);
            System.out.println("SUCCESS");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        request.setAttribute("location", (String) currentUser[4]);
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        response.sendRedirect("login");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
