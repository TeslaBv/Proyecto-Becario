<%-- 
    Document   : login
    Created on : 13/10/2024, 06:10:35 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.Cookie"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar Sesión</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5" style="width: 40%;">
            <div style="margin-top: 2vh">
                <a href=".." class="btn btn-lg" style="background: transparent; color: green; border-color: green">Regresar</a>
            </div>
            <h1 class="text-center mb-4">Iniciar Sesión</h1>
            <form action="${pageContext.request.contextPath}/login_servlet" method="post">
                <div class="mb-3">
                    <label for="curp" class="form-label">CURP</label>
                    <input type="text" class="form-control" id="curp" name="curp" maxlength="18" required 
                           <%
                               // Buscar la cookie de CURP
                               Cookie[] cookies = request.getCookies();
                               String curpValue = "";
                               if (cookies != null) {
                                   for (Cookie cookie : cookies) {
                                       if ("CURP".equals(cookie.getName())) {
                                           curpValue = cookie.getValue();
                                           break;
                                       }
                                   }
                               }
                               // Establecer el valor de la cookie en el input si existe
                               if (!curpValue.isEmpty()) {
                           %>
                           value="<%= curpValue%>"
                           <% }%>
                           >
                </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="pwd" name="pwd" required>
                </div>
                <div class="mb-3" style="display: none">
                    <label for="pwd" class="form-label">Login</label>
                    <input type="password" class="form-control" id="login" name="login" value="true" required>
                </div>

                <button type="submit" class="btn btn-lg" style="background: green; color: white; display: block">Iniciar Sesión</button>
            </form>
        </div>

        <!-- Bootstrap 5 JS (optional for interactivity) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
