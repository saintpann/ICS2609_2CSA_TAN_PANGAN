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
import Objects.IncorrectPassException;
import Objects.IncorrectUserPassException;
import Objects.NoUsernameFoundException;
import Objects.NullValueException;

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
            throws ServletException, IOException, NullValueException,
            IncorrectPassException, IncorrectUserPassException,
            NoUsernameFoundException {
//        try {
            //Checking Database if the username and password exists
            if (conn!=null){
                //System.out.println("Getting Connectio n..");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
//                checks if username and password are blank
                String message = "";
                System.out.println("this ran");
                try{
                    if(username.trim().equals("")&&password.trim().equals("")){                    
                        message = "Username and Password cannot be blank";
                        throw new NullValueException(message);
                    }
                
                
                    PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE Email = ?");
                    ps.setString(1, username);

                    ResultSet rs = ps.executeQuery();
    //              while loop to find each row if anything matches
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
                            message = "Correct Username but Wrong Password";
                            throw new IncorrectPassException(message);
                        }
                    }
                    else{
                        if(password.equals("")){
                            message = "Incorrect Username and Empty Password";
                            throw new NoUsernameFoundException(message);
                        }
                        else{
                            message = "Incorrect Username and Password";
                            response.sendRedirect("error_3.jsp");
                            throw new IncorrectUserPassException(message);
                        }
                    }
                } 
                catch (SQLException sqle){
                    throw new NoUsernameFoundException("Username not Found");
                }
                
            }
            
//        }
//        catch(SQLException sqle){
//            System.err.println("SQL EXCEPTION TRIGGERED!");
//            System.err.println("Error Message: " + sqle.getMessage());
//            
//            throw new NoUsernameFoundException();
//            response.sendRedirect("error_1.jsp");
//        }
//        catch(NullValueException nv){
//            throw new NullValueException();
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for processing login data before providing user access";
    }

//    private static class NullValueException extends Exception {
//        String msg;
//        public NullValueException(String errormsg) {
//            this.msg = errormsg;
//        }
//        @Override
//        public String getMessage(){
//            return this.msg;
//        }
//    }

}
