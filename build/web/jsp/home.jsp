<%-- 
    Document   : home
    Created on : 13/10/2024, 07:19:32 PM
    Author     : CruzF
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@page import="java.sql.*" %>
<%
    // Verificar si el usuario está logueado y si no mandarlo a login
    if (session == null || session.getAttribute("CURP") == null) {
        response.sendRedirect("login.jsp");
    }

    // Obtener el CURP de la sesión
    String curp = (String) session.getAttribute("CURP");

    // Tiempo de sesión en segundos
    int tiempoSesionSegundos = session.getMaxInactiveInterval();

    // Variables para almacenar los datos del becario
    String apPat = "", apMat = "", nombre = "", genero = "", fNac = "", foto = "";

    // Variable para verificar si ya existe un registro en la tabla vivienda
    boolean existeVivienda = false;

    // Conexión a la base de datos
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Establecer conexión (aquí debes ajustar con tus propios parámetros de conexión)
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba", "root", "");

        // Realizar la consulta a la base de datos para el becario
        String sql = "SELECT apPat, apMat, nombre, genero, fNac, foto FROM becario WHERE curp = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, curp);
        rs = ps.executeQuery();

        if (rs.next()) {
            // Obtener los datos del resultado
            apPat = rs.getString("apPat");
            apMat = rs.getString("apMat");
            nombre = rs.getString("nombre");
            genero = rs.getString("genero");
            fNac = rs.getString("fNac");
            foto = rs.getString("foto"); // Obtener la ruta de la foto
        } else {
            out.println("<p>Error: Becario no encontrado</p>");
        }

        // Consulta para verificar si existe un registro en la tabla vivienda
        String sqlVivienda = "SELECT COUNT(*) FROM vivienda WHERE curp = ?";
        ps = conn.prepareStatement(sqlVivienda);
        ps.setString(1, curp);
        rs = ps.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            existeVivienda = true; // Hay un registro en la tabla vivienda
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try {
            rs.close();
        } catch (SQLException e) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException e) {
        }
        if (conn != null) try {
            conn.close();
        } catch (SQLException e) {
        }
    }

    Long tiempoExpiracionCookie = (Long) session.getAttribute("tiempoExpiracionCookie");
    long tiempoRestanteCookie = (tiempoExpiracionCookie != null) ? (tiempoExpiracionCookie - System.currentTimeMillis()) / 1000 : 0; // en segundos
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página del Becario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .profile-pic {
                width: 100px;
                height: 100px;
                background-color: lightgray;
                display: flex;
                justify-content: center;
                align-items: center;
                font-size: 1.5em;
                color: darkgray;
                border-radius: 50%;
            }
        </style>
        <script>
            var tiempoSesion = <%= tiempoSesionSegundos%>;
            var tiempoRestanteCookie = <%= tiempoRestanteCookie%>; // Tiempo restante de la cookie en segundos

            function iniciarCronometro() {
                var cronometroSesion = document.getElementById("cronometroSesion");
                var cronometroCookie = document.getElementById("cronometroCookie");

                // Actualizar el cronómetro de sesión
                var intervalo = setInterval(function () {
                    if (tiempoSesion > 0) {
                        tiempoSesion--;
                        var minutos = Math.floor(tiempoSesion / 60);
                        var segundos = tiempoSesion % 60;

                        // Mostrar el tiempo en un string
                        cronometroSesion.innerHTML = "Tiempo restante de sesión: " + minutos + " min " + (segundos < 10 ? "0" : "") + segundos + " seg";
                    } else {
                        clearInterval(intervalo);
                        cronometroSesion.innerHTML = "La sesión ha expirado";
                        // Redirigir al login cuando el tiempo de sesión acabe
                        setTimeout(function () {
                            window.location.href = 'login.jsp';
                        }, 1000);
                    }
                }, 1000);

                // Actualizar el cronómetro de cookie
                var intervaloCookie = setInterval(function () {
                    if (tiempoRestanteCookie > 0) {
                        tiempoRestanteCookie--;
                        var minutosCookie = Math.floor(tiempoRestanteCookie / 60);
                        var segundosCookie = tiempoRestanteCookie % 60;

                        // Mostrar el tiempo en un string
                        cronometroCookie.innerHTML = "Tiempo restante de cookie: " + minutosCookie + " min " + (segundosCookie < 10 ? "0" : "") + segundosCookie + " seg";
                    } else {
                        clearInterval(intervaloCookie);
                        cronometroCookie.innerHTML = "La cookie ha expirado";
                        // Redirigir al login cuando la cookie expira
                        setTimeout(function () {
                            window.location.href = 'login.jsp';
                        }, 1000);
                    }
                }, 1000);
            }
        </script>
    </head>
    <body onload="iniciarCronometro()">
        <div class="container mt-5">
            <div style="margin-top: 2vh;">
                <a href="../index.html" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Cerrar Sesion</a>
            </div>
            <h1 class="text-center">Bienvenido, <%= nombre%>!</h1>

            <!-- Cronómetro de sesión -->
            <div class="text-center">
                <h3 id="cronometroSesion">Tiempo restante de sesión:</h3>
            </div>
            <!-- Cronómetro de cookie -->
            <div class="text-center">
                <h3 id="cronometroCookie">Tiempo restante de cookie:</h3>
            </div>

            <!-- Imagen de perfil -->
            <div class="d-flex justify-content-center my-4">
                <div class="profile-pic">
                    <img src="<%= request.getContextPath() + "/" + foto%>" alt="imgPerfil" style="width: 100%; height: 100%; border-radius: 50%;">
                </div>
            </div>
            <div class="d-flex justify-content-center my-4">
                <% if (foto == null || foto.isEmpty()) { %>
                <a href="./selectImage.jsp" class="btn btn-lg" style="background: #00cc00; color: white; border-color: #00cc00">Agregar Foto</a>
                <% }%>
            </div>

            <!-- Datos del becario -->
            <h2>Datos del Becario:</h2>
            <table class="table table-bordered table-striped">
                <tr>
                    <th>CURP</th>
                    <td><%= curp%></td>
                </tr>
                <tr>
                    <th>Apellido Paterno</th>
                    <td><%= apPat%></td>
                </tr>
                <tr>
                    <th>Apellido Materno</th>
                    <td><%= apMat%></td>
                </tr>
                <tr>
                    <th>Nombre</th>
                    <td><%= nombre%></td>
                </tr>
                <tr>
                    <th>Género</th>
                    <td><%= genero%></td>
                </tr>
                <tr>
                    <th>Fecha de Nacimiento</th>
                    <td><%= fNac%></td>
                </tr>
            </table>
            <div style="margin-top: 2vh;">
                <% if (!existeVivienda) { %>
                <a href="./jsp/vivienda.jsp" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Registrar Vivienda</a>
                <% }%>
            </div>
            <div style="margin-top: 2vh;">
                <a href="../vivienda_servlet" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Consultar Vivienda</a>
            </div>
        </div>
    </body>
</html>
