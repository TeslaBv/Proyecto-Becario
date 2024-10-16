<%-- 
    Document   : vivienda
    Created on : 13/10/2024, 08:34:47 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Vivienda</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div style="margin-top: 2vh;">
                <a href="./home.jsp" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Regresar</a>
            </div>
            <h1 class="text-center mb-4">Agregar Vivienda</h1>
            <form action="${pageContext.request.contextPath}/vivienda_servlet" method="post">
                <div class="mb-3">
                    <label for="calle" class="form-label">Calle</label>
                    <input type="text" class="form-control" id="calle" name="calle" placeholder="Ingresa la calle" required>
                </div>
                <div class="mb-3">
                    <label for="colonia" class="form-label">Colonia</label>
                    <input type="text" class="form-control" id="colonia" name="colonia" placeholder="Ingresa la colonia" required>
                </div>
                <div class="mb-3">
                    <label for="municipio" class="form-label">Municipio</label>
                    <input type="text" class="form-control" id="municipio" name="municipio" placeholder="Ingresa el municipio" required>
                </div>
                <div class="mb-3">
                    <label for="cp" class="form-label">Código Postal</label>
                    <input type="text" class="form-control" id="cp" name="cp" placeholder="Ingresa el código postal" required>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn" style="background: #009900; color: white; border-color: #009900">Agregar Vivienda</button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS (opcional para características avanzadas) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
