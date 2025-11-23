/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DBCon.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.UsrAgenda;
import java.sql.*;

public class InsertContact extends HttpServlet {
    Statement stmt = null;
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
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Insert Contact</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Insert Contact Result</h1>");
            
            if (nombre != null && apellido != null && correo != null && telefono != null) {
                DBConnection dbc = new DBConnection();
                Connection conn = null;
                PreparedStatement pstmt = null;
                
                try {
                    conn = dbc.getDbConn();
                    if (conn == null) {
                        out.println("<h2>Error: No se pudo establecer conexión a la base de datos</h2>");
                        return;
                    }
                    String sql = "INSERT INTO users (first_name, last_name, email, telefono) VALUES (?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    
                    pstmt.setString(1, nombre);
                    pstmt.setString(2, apellido);
                    pstmt.setString(3, correo);
                    pstmt.setString(4, telefono);
                    
                    int result = pstmt.executeUpdate();
                    
                    if (result > 0) {
                        out.println("<h2>Contacto insertado exitosamente</h2>");
                        out.println("<p>Nombre: " + escapeHtml(nombre) + "</p>");
                        out.println("<p>Apellido: " + escapeHtml(apellido) + "</p>");
                        out.println("<p>Correo: " + escapeHtml(correo) + "</p>");
                        out.println("<p>Telefono: " + escapeHtml(telefono) + "</p>");
                    } else {
                        out.println("<h2>Error al insertar contacto</h2>");
                    }
                } catch (SQLException e) {
                    out.println("<h2>Error de base de datos: " + escapeHtml(e.getMessage()) + "</h2>");
                    out.println("<p>Código de error: " + e.getErrorCode() + "</p>");
                    out.println("<p>Estado SQL: " + e.getSQLState() + "</p>");
                    e.printStackTrace();
                } catch (Exception e) {
                    out.println("<h2>Error general: " + escapeHtml(e.getMessage()) + "</h2>");
                    e.printStackTrace();
                } finally {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                }
            } else {
                out.println("<h2>Error: Todos los campos son requeridos</h2>");
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
            System.getLogger(InsertContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in InsertContact", ex);
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
            System.getLogger(InsertContact.class.getName()).log(System.Logger.Level.ERROR, "Database error in InsertContact", ex);
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
        return "Servlet for inserting contact information into the database";
    }// </editor-fold>
}
