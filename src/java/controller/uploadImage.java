/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "uploadImage", urlPatterns = {"/upload_image"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class uploadImage extends HttpServlet {

    private static final String UPLOAD_DIR = "images"; // Carpeta donde se guardarán las imágenes

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // Método para obtener el nombre del archivo subido
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }

    // Método para guardar la ruta de la imagen en la base de datos
    private void saveImagePathToDatabase(HttpServletRequest request, String imagePath) {
        try {
            // Extraer la CURP de la sesión
            HttpSession session = request.getSession(false);  // false no crea una nueva sesión si no existe
            String curp = (String) session.getAttribute("CURP");

            if (curp == null) {
                System.out.println("No hay CURP en la sesión.");
                return;
            }

            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();

            String sql = "UPDATE becario SET foto = ? WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, imagePath);
            ps.setString(2, curp);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Imagen actualizada correctamente para la CURP: " + curp);
            } else {
                System.out.println("No se pudo actualizar la imagen para la CURP: " + curp);
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error al actualizar la imagen: " + e);
        }
    }

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
            out.println("<title>Servlet uploadImage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet uploadImage at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // Obtener la ruta absoluta de la carpeta "images"
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // Crear la carpeta "images" si no existe
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Crear directorios si no existen
        }

        // Obtener la imagen subida
        Part part = request.getPart("image");
        String fileName = getFileName(part);

        // Guardar la imagen en el servidor (en la carpeta "images")
        String filePath = uploadFilePath + File.separator + fileName;
        part.write(filePath);

        // Guardar la ruta relativa de la imagen (para almacenarla en la base de datos)
        String relativePath = UPLOAD_DIR + File.separator + fileName;
        System.out.println("Path: " + relativePath);

        // Guardar la ruta de la imagen en la base de datos
        saveImagePathToDatabase(request, relativePath);

        // Responder al usuario
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
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
