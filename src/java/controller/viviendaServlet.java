/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "viviendaServlet", urlPatterns = {"/vivienda_servlet"})
public class viviendaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet viviendaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viviendaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();

        // Obtener CURP de la sesión
        HttpSession session = request.getSession();
        String curp = (String) session.getAttribute("CURP");
        if (curp == null) {
            System.out.println("No se encontró CURP en la sesión.");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Consulta SQL para obtener los datos de las tablas becario y vivienda
            String sql = "SELECT b.curp, b.apPat, b.apMat, b.nombre, b.genero, b.fNac, "
                    + "v.calle, v.colonia, v.municipio, v.cp "
                    + "FROM becario b "
                    + "LEFT JOIN vivienda v ON b.curp = v.curp "
                    + "WHERE b.curp = ?";

            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);

            rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("curp", rs.getString("curp"));
                request.setAttribute("apPat", rs.getString("apPat"));
                request.setAttribute("apMat", rs.getString("apMat"));
                request.setAttribute("nombre", rs.getString("nombre"));
                request.setAttribute("genero", rs.getString("genero"));
                request.setAttribute("fNac", rs.getDate("fNac"));
                request.setAttribute("calle", rs.getString("calle"));
                request.setAttribute("colonia", rs.getString("colonia"));
                request.setAttribute("municipio", rs.getString("municipio"));
                request.setAttribute("cp", rs.getString("cp"));
            }

            // Redirigir a la página JSP para mostrar los datos
            request.getRequestDispatcher("/jsp/datos.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        System.out.println("Entro al doPost");
        ConnectionBD conexion = new ConnectionBD();
        Connection conn = null;
        PreparedStatement ps = null;

        // Obtener CURP de la sesión
        HttpSession session = request.getSession();
        String curp = (String) session.getAttribute("CURP");
        if (curp == null) {
            System.out.println("No se encontró CURP en la sesión.");
            response.sendRedirect("login.jsp");
            return;
        }

        // Obtener los parámetros del formulario
        String calle = request.getParameter("calle");
        String colonia = request.getParameter("colonia");
        String municipio = request.getParameter("municipio");
        String cp = request.getParameter("cp");

        // Imprimir los valores obtenidos en consola
        System.out.println("CURP: " + curp);
        System.out.println("Calle: " + calle);
        System.out.println("Colonia: " + colonia);
        System.out.println("Municipio: " + municipio);
        System.out.println("Código Postal: " + cp);

        try {
            // Ajusta la consulta SQL para que coincida con los parámetros
            String sql = "INSERT INTO vivienda (curp, calle, colonia, municipio, cp) VALUES (?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);      // CURP desde la sesión
            ps.setString(2, calle);     // Datos obtenidos del formulario
            ps.setString(3, colonia);
            ps.setString(4, municipio);
            ps.setString(5, cp);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Vivienda insertada correctamente.");
                response.sendRedirect(request.getContextPath() + "/jsp/home.jsp");
            } else {
                System.out.println("No se insertó la vivienda.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Cerrar recursos (conn, ps, etc.)
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
