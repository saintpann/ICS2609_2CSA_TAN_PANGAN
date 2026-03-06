/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import miscs.EstablishConnection;
import miscs.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Saintan
 */
public class CRUDServlet extends HttpServlet {

    private Connection conn;
    
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        conn = new EstablishConnection(getServletContext()).getConnection();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String clickbtn = request.getParameter("btn") == null ? "" : request.getParameter("btn").trim();
        String username = request.getParameter("username") == null ? "" : request.getParameter("username").trim();
        String password = request.getParameter("password") == null ? "" : request.getParameter("password").trim();
        String role     = request.getParameter("role") == null ? "" : request.getParameter("role").trim();
        
        String query = "";
        if (clickbtn.equals("delete")) {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            if(username.equals(user.getUsername())){response.sendRedirect("success");}
            query = "DELETE FROM USERS WHERE Email = ?";
        } else if (clickbtn.equals("update")) {
            query = "UPDATE USERS SET Password = ?, UserRole = ? WHERE Email = ?";
        } else if (clickbtn.equals("add")) {
            query = "INSERT INTO Users(Email, Password, UserRole) VALUES (?,?,?)";
        }
        
        
        if(!query.isEmpty()){
        try(PreparedStatement ps = conn.prepareStatement(query)){
            if(clickbtn.equals("delete")){
                ps.setString(1, username);
            }else if(clickbtn.equals("update")){
                ps.setString(1, password);
                ps.setString(2, role);
                ps.setString(3, username);
            }else if(clickbtn.equals("add")){
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, role);
            }
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Exception");
        }
        response.sendRedirect("success");
    }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(500);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
