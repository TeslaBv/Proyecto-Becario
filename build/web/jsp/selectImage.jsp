<%-- 
    Document   : selectImage
    Created on : 13/10/2024, 08:55:02 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Subir Imagen</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div style="margin-top: 2vh;">
                <a href="./home.jsp" class="btn btn-lg" style="background: #009900; color: white; border-color: #009900">Regresar</a>
            </div>
            <h1 class="text-center mb-4">Subir Imagen</h1>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <form action="${pageContext.request.contextPath}/upload_image" method="post" enctype="multipart/form-data" class="bg-light p-4 rounded shadow">
                        <div class="mb-3">
                            <label for="image" class="form-label">Selecciona una imagen</label>
                            <input type="file" name="image" id="image" class="form-control" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Subir Imagen</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS (opcional) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
