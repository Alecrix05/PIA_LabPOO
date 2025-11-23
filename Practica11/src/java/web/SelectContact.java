/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web;

import DBCon.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UsrAgenda;
import java.sql.*;

/**
 *
 * @author Alec
 */
public class SelectContact extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Select Contacts</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Contactos</h1>");
            
            DBConnection dbc = new DBConnection();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                conn = dbc.getDbConn();
                String sql = "SELECT * FROM users";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                
                out.println("<h2>Contactos en la base de datos:</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nombre</th><th>Apellido</th><th>Correo</th><th>Telefono</th></tr>");
                
                boolean hasResults = false;
                while(rs.next()) {
                    hasResults = true;
                    out.println("<tr>");
                    out.println("<td>" + escapeHtml(rs.getString("id")) + "</td>");
                    out.println("<td>" + escapeHtml(rs.getString("first_name")) + "</td>");
                    out.println("<td>" + escapeHtml(rs.getString("last_name")) + "</td>");
                    out.println("<td>" + escapeHtml(rs.getString("email")) + "</td>");
                    out.println("<td>" + escapeHtml(rs.getString("telefono")) + "</td>");
                    out.println("</tr>");
                }
                
                if (!hasResults) {
                    out.println("<tr><td colspan='5'>No hay contactos registrados</td></tr>");
                }
                
                out.println("</table>");
                
            } catch (SQLException e) {
                out.println("<h2>Error de base de datos: " + escapeHtml(e.getMessage()) + "</h2>");
            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            System.getLogger(SelectContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in SelectContact", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            System.getLogger(SelectContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in SelectContact", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet that retrieves and displays contact information from the users database";
    }// </editor-fold>

}