<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Listar usuarios - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>Listar usuarios</h2>
    <a href="${pageContext.request.contextPath}/jsp/formulariousuarios.jsp">Nuevo Usuario</a>
    <br/><br/>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Usuario</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="usuario" items="${usuarios}">
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.usuario}</td>
                <td>${usuario.rol}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/usercrud/editar?id=${usuario.id}">Editar</a> |
                    <a href="${pageContext.request.contextPath}/usercrud/eliminar?id=${usuario.id}" onclick="return confirm('¿Está seguro de eliminar este usuario?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a>
</body>
</html>
