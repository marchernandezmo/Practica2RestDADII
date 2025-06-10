<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Listar espacios - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>Listar espacios</h2>
    <a href="${pageContext.request.contextPath}/jsp/formularioespacios.jsp">Nuevo Espacio</a>
    <br/><br/>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripci&oacute;n</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="espacio" items="${espacios}">
            <tr>
                <td>${espacio.id}</td>
                <td>${espacio.nombre}</td>
                <td>${espacio.description}</td>
                <td>
                    <!-- Para editar utilizando la URL /espaciocrud/editar?id=2 -->
                    <a href="${pageContext.request.contextPath}/espaciocrud/editar?id=${espacio.id}">Editar</a> |
                    <!-- Para eliminar -->
                    <a href="${pageContext.request.contextPath}/espaciocrud/eliminar?id=${espacio.id}" onclick="return confirm('¿Está seguro de eliminar este espacio?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a>
</body>
</html>
