
package controller;

import miscs.AuthenticationException;
import miscs.EstablishConnection;
import miscs.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
public class SuccessServlet extends HttpServlet {
    private Connection conn;
    
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        conn = new EstablishConnection(getServletContext()).getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AuthenticationException {
        HttpSession session = request.getSession(false);
        
        if(session==null){
            String message = "Your session has expired. Please log in again.";
            throw new AuthenticationException(message);
        }
        
        User currentuser = (User) session.getAttribute("user");
        if(currentuser==null){
            String message = "Your session has expired. Please log in again.";
            throw new AuthenticationException(message);
        }
        String role = currentuser.getRole();
        if(role.equals("Admin")){
            List<String[]> list = new ArrayList<>();
            try{
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM USERS");
                while(rs.next()){

                    String user = rs.getString("Email");
                    String pass = rs.getString("Password");
                    String urole = rs.getString("UserRole");

                    list.add(new String[]{user,pass,urole});
                }
                request.setAttribute("list", list);
            }
            catch(SQLException e){
                System.out.println("Connection Error");
            }
        }
        request.getRequestDispatcher("success.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
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
