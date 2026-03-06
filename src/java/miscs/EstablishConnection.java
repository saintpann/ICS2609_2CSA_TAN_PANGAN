/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miscs;

import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import java.sql.Connection;

/**
 *
 * @author Kimberly
 */
public class EstablishConnection {
    private Connection conn = null;
    
    public EstablishConnection(ServletContext sc){
        try{
        Class.forName(sc.getInitParameter("jdbcClassName"));
        System.out.println("Connecting to jdbcClassName: " + sc.getInitParameter("jdbcClassName"));
        String username = sc.getInitParameter("dbUserName");
        String password = sc.getInitParameter("dbPassword");
        StringBuffer url = new StringBuffer(sc.getInitParameter("jdbcDriverURL"))
                        .append("://")
                        .append(sc.getInitParameter("dbHostName"))
                        .append(":")
                        .append(sc.getInitParameter("dbPort"))
                        .append("/")
                        .append(sc.getInitParameter("databaseName"));
        String urls = url.toString();
        System.out.println("Connecting to connection.. "+urls);
        conn = DriverManager.getConnection(urls,username,password);
        System.out.println(conn);
        } catch (SQLException sqle){
                    System.out.println("SQLException error occured - " 
                            + sqle.getMessage());
        } catch (ClassNotFoundException nfe){
                    System.out.println("ClassNotFoundException error occured - " 
                    + nfe.getMessage());
        }
    }
    
    public Connection getConnection(){
        return conn;
    }
}
