/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "becarioServlet", urlPatterns = {"/becario_servlet"})
public class becarioServlet extends HttpServlet {

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
            out.println("<title>Servlet becarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet becarioServlet at " + request.getContextPath() + "</h1>");
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
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Extraer la CURP de la sesión
            HttpSession session = request.getSession(false); 
            String curp = (String) session.getAttribute("CURP"); 

            if (curp == null) {
                response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
                return;
            }

            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();

            // Consulta SQL para obtener los datos del becario (Aunque la foto sea nul)
            String sql = "SELECT * FROM becario WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp); // Establecer la CURP en la consulta

            rs = ps.executeQuery();

            // Verificar si hay resultados
            if (rs.next()) {
                // Obtener los datos de la tabla becario
                String apPat = rs.getString("apPat");
                String apMat = rs.getString("apMat");
                String nombre = rs.getString("nombre");
                String genero = rs.getString("genero");
                String fNac = rs.getString("fNac");
                String foto = rs.getString("foto"); // Suponiendo que hay una columna foto

                // Pasar los datos como atributos para la JSP
                request.setAttribute("curp", curp);
                request.setAttribute("apPat", apPat);
                request.setAttribute("apMat", apMat);
                request.setAttribute("nombre", nombre);
                request.setAttribute("genero", genero);
                request.setAttribute("fNac", fNac);
                request.setAttribute("foto", foto);

                // Redirigir a la página JSP para mostrar los datos
                request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
            } else {
                // Manejo si no se encuentra el becario con la CURP
                System.out.println("No se encontró el becario con CURP: " + curp);
                response.sendRedirect(request.getContextPath() + "/jsp/error.jsp"); // Redirigir a una página de error
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores: puedes redirigir a una página de error o mostrar un mensaje
            response.sendRedirect(request.getContextPath() + "/jsp/error.jsp");
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
            } catch (SQLException e) {
                e.printStackTrace();
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

        // Obtener los parámetros del formulario 
        String curp = request.getParameter("curp");
        String apPat = request.getParameter("apPat");
        String apMat = request.getParameter("apMat");
        String nombre = request.getParameter("nombre");
        String genero = request.getParameter("genero");

        String pwd = request.getParameter("pwd");
        pwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        // Extraer la fecha de nacimiento de la CURP
        String fNac = null;
        try {
            String year = curp.substring(4, 6);  
            String month = curp.substring(6, 8); 
            String day = curp.substring(8, 10);  

            // Determinar el siglo (siglo XX o XXI)
            int yearInt = Integer.parseInt(year);
            if (yearInt <= 24) {
                year = "20" + year;  
            } else {
                year = "19" + year;
            }

            // Formatear la fecha en formato YYYY-MM-DD
            fNac = year + "-" + month + "-" + day;
            System.out.println("Fecha de nacimiento extraída de la CURP: " + fNac);
        } catch (Exception e) {
            System.out.println("Error al extraer la fecha de nacimiento de la CURP: " + e.getMessage());
        }

        // Imprimir los valores obtenidos en consola
        System.out.println("CURP: " + curp);
        System.out.println("Apellido Paterno: " + apPat);
        System.out.println("Apellido Materno: " + apMat);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha de Nacimiento: " + fNac);
        System.out.println("Género: " + genero);
        System.out.println("Contraseña: " + pwd);

        
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = java.sql.Date.valueOf(fNac);  // Convertimos el String a Date pero de java.sql.Date
        } catch (IllegalArgumentException e) {
            System.out.println("Error al convertir la fecha de nacimiento: " + e.getMessage());
        }

        try {
            
            String sql = "INSERT INTO becario (curp, apPat, apMat, nombre, genero, pwd, fNac) VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);
            ps.setString(2, apPat);
            ps.setString(3, apMat);
            ps.setString(4, nombre);
            ps.setString(5, genero);
            ps.setString(6, pwd);
            ps.setDate(7, fechaNacimiento);  

            
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Se insertó correctamente.");
                response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            } else {
                System.out.println("No se insertó.");
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
