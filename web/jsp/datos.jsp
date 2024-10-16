<%-- 
    Document   : datos
    Created on : 13/10/2024, 08:48:55 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Datos del Becario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div style="margin-top: 2vh;">
                <a href="jsp/home.jsp" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Regresar</a>
            </div>
            <h2>Datos del Becario</h2>
            <table class="table table-bordered">
                <tr>
                    <th>CURP</th>
                    <td>${curp}</td>
                </tr>
                <tr>
                    <th>Apellido Paterno</th>
                    <td>${apPat}</td>
                </tr>
                <tr>
                    <th>Apellido Materno</th>
                    <td>${apMat}</td>
                </tr>
                <tr>
                    <th>Nombre</th>
                    <td>${nombre}</td>
                </tr>
                <tr>
                    <th>Género</th>
                    <td>${genero}</td>
                </tr>
                <tr>
                    <th>Fecha de Nacimiento</th>
                    <td>${fNac}</td>
                </tr>
            </table>

            <h2>Datos de la Vivienda</h2>
            <table class="table table-bordered">
                <tr>
                    <th>Calle</th>
                    <td>${calle}</td>
                </tr>
                <tr>
                    <th>Colonia</th>
                    <td>${colonia}</td>
                </tr>
                <tr>
                    <th>Municipio</th>
                    <td>${municipio}</td>
                </tr>
                <tr>
                    <th>Código Postal</th>
                    <td>${cp}</td>
                </tr>
            </table>
        </div>
    </body>
</html>

