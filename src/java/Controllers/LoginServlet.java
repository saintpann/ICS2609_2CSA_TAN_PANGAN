/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
public class LoginServlet extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Checking Database if the username and password exists
            if (conn!=null){
                //System.out.println("Getting Connectio n..");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                if(username.equals("")&&password.equals("")){
                    throw new NullValueException("Username and Password cannot be blank");
                }
                
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE Email = ?");
                ps.setString(1, username);
                
                //System.out.println("Executing Statement..");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(rs.getString("password").equals(password)){
                    String role = rs.getString("UserRole");
                    List<String[]> list = new ArrayList<>();
                    Statement st = conn.createStatement();
                    String query = "SELECT * FROM USERS";
                    rs = st.executeQuery(query);
                    while(rs.next()){
                        String user = rs.getString("Email");
                        String pass = rs.getString("Password");
                        String urole = rs.getString("UserRole");
                        
                        list.add(new String[]{user,pass,urole});
                    }
                    HttpSession session = request.getSession(true);
                    session.setAttribute("role", role);
                    request.setAttribute("result", rs);
                    RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
                    rd.forward(request, response);
                    }
                    else{
                        System.out.println("Correct Username but Wrong Password");
                        response.sendRedirect("error_2.jsp");
                    }
                }
                else{
                    if(password.equals("")){
                        System.out.println("Incorrect Username and Empty Password");
                        response.sendRedirect("error_1.jsp");
                    }
                    else{
                        System.out.println("Incorrect Username and Password");
                        response.sendRedirect("error_3.jsp");
                    }
                }
                
            }
            
        }
        catch(SQLException sqle){
            System.err.println("SQL EXCEPTION TRIGGERED!");
            System.err.println("Error Message: " + sqle.getMessage());
            response.sendRedirect("error_1.jsp");
        }
        catch(NullValueException nv){
            System.err.println("Error Message:" + nv.getMessage());
            response.sendRedirect("noLoginCredentials.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("error_session.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for processing login data before providing user access";
    }// </editor-fold>

    private static class NullValueException extends Exception {
        String msg;
        public NullValueException(String errormsg) {
            this.msg = errormsg;
        }
        @Override
        public String getMessage(){
            return this.msg;
        }
    }

}
