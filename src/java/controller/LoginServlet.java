/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miscs.IncorrectPassException;
import miscs.IncorrectUserPassException;
import miscs.NoUsernameFoundException;
import miscs.NullValueException;
import miscs.User;
import miscs.EstablishConnection;
/**
 *
 * @author Saintan
 */
public class LoginServlet extends HttpServlet {

    private Connection conn;
    
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        conn = new EstablishConnection(getServletContext()).getConnection();
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
                try{
                    if(username.trim().equals("")&&password.trim().equals("")){                    
                        message = "Username and Password cannot be blank";
                        System.out.println("Should go to noLoginCredentials.jsp");
                        throw new NullValueException(message);
                    }
                    try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE Email = ?")){
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
    //              while loop to find each row if anything matches
                    if (rs.next()) {
                        if(rs.getString("password").equals(password)){
                        String role = rs.getString("UserRole");
                        HttpSession session = request.getSession(true);
                        User currentuser = new User();
                        currentuser.setUsername(username);
                        currentuser.setPassword(password);
                        currentuser.setRole(role);
                        
                        session.setAttribute("user", currentuser);
                        response.sendRedirect("success");
                        return;
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
                            throw new IncorrectUserPassException(message);
                        }
                    }
                    }
                } 
                catch (SQLException sqle){
                    throw new NoUsernameFoundException("Username not Found");
                }
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
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        response.sendRedirect("login");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for processing login data before providing user access";
    }

}
