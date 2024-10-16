<%-- 
    Document   : register
    Created on : 13/10/2024, 06:10:24 PM
    Author     : CruzF
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alta de Becario</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5" style="width: 40%;">
            <div style="margin-top: 2vh">
                <a href=".." class="btn btn-lg" style="background: transparent; color: green; border-color: green">Regresar</a>
            </div>
            <h1 class="text-center mb-4">Alta de Becario</h1>
            <form action="${pageContext.request.contextPath}/becario_servlet" method="post">
                <div class="mb-3">
                    <label for="curp" class="form-label">CURP</label>
                    <input type="text" class="form-control" id="curp" name="curp" maxlength="18" required>
                </div>
                <div class="mb-3">
                    <label for="apPat" class="form-label">Apellido Paterno</label>
                    <input type="text" class="form-control" id="apPat" name="apPat" required>
                </div>
                <div class="mb-3">
                    <label for="apMat" class="form-label">Apellido Materno</label>
                    <input type="text" class="form-control" id="apMat" name="apMat" required>
                </div>
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="pwd" name="pwd" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Género</label>
                    <div>
                        <input type="radio" id="generoM" name="genero" value="Masculino" required>
                        <label for="generoM">Masculino</label>
                    </div>
                    <div>
                        <input type="radio" id="generoF" name="genero" value="Femenino" required>
                        <label for="generoF">Femenino</label>
                    </div>
                </div>
                <button type="submit" class="btn btn-lg" style="background: green; color: white; display: block">Registrar</button>

            </form>
                
        </div>

        <!-- Bootstrap 5 JS (optional for interactivity) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
