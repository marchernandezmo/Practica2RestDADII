<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Inicio - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>
        <c:choose>
            <c:when test="${not empty sessionScope.usuario}">
                ¡Bienvenid@ de nuevo a la reserva de espacios de MaHerMo, ${sessionScope.usuario.usuario}!
            </c:when>
            <c:otherwise>
                Bienvenid@ a la reserva de espacios de MaHerMo
            </c:otherwise>
        </c:choose>
    </h2>
    <ul>
		<li><a href="jsp/espacios.jsp">Gestión de Espacios</a></li>
		<li><a href="jsp/reservas.jsp">Gestión de Reservas</a></li>
		<li><a href="jsp/logout.jsp">Cerrar sesión</a></li>
    </ul>
    <h1>Espacios Disponibles</h1>
    <ul id="lista-espacios"></ul>
</body>
</html>