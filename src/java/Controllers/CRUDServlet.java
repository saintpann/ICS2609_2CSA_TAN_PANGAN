/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Objects.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
        try{
            Class.forName(config.getInitParameter("jdbcClassName"));
            System.out.println("Connecting to jdbcClassName: " + config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL"))
                            .append("://")
                            .append(config.getInitParameter("dbHostName"))
                            .append(":")
                            .append(config.getInitParameter("dbPort"))
                            .append("/")
                            .append(config.getInitParameter("databaseName"));
            System.out.println("Connecting to connection.. "+url);
            String urls ="jdbc:derby://localhost:1527/LoginDB";
            conn = DriverManager.getConnection(urls,username,password);
            System.out.println(conn);
        }
        catch (SQLException sqle){
                    System.out.println("SQLException error occured - " 
                            + sqle.getMessage());
        } catch (ClassNotFoundException nfe){
                    System.out.println("ClassNotFoundException error occured - " 
                    + nfe.getMessage());
            }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
