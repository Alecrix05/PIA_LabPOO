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
public class DeleteContact extends HttpServlet {

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
        
        String id = request.getParameter("id");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Delete Contact</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Delete Contact Result</h1>");
            
            if (id != null && !id.trim().isEmpty()) {
                DBConnection dbc = new DBConnection();
                Connection conn = null;
                PreparedStatement pstmt = null;
                
                try {
                    conn = dbc.getDbConn();
                    String sql = "DELETE FROM users WHERE id = ?";
                    pstmt = conn.prepareStatement(sql);
                    
                    pstmt.setString(1, id);
                    
                    int result = pstmt.executeUpdate();
                    
                    if (result > 0) {
                        out.println("<h2>Contacto eliminado exitosamente</h2>");
                        out.println("<p>ID eliminado: " + escapeHtml(id) + "</p>");
                    } else {
                        out.println("<h2>No se encontró el contacto con ID: " + escapeHtml(id) + "</h2>");
                    }
                } catch (SQLException e) {
                    out.println("<h2>Error de base de datos: " + escapeHtml(e.getMessage()) + "</h2>");
                } finally {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                }
            } else {
                out.println("<h2>Error: ID es requerido para eliminar contacto</h2>");
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
            System.getLogger(DeleteContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in DeleteContact", ex);
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
            System.getLogger(DeleteContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in DeleteContact", ex);
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
        return "Servlet for deleting contacts from the user agenda";
    }// </editor-fold>

}
