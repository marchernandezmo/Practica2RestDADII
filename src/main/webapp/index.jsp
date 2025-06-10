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
        <c:choose>
            <c:when test="${not empty sessionScope.usuario}">
                <li><a href="${pageContext.request.contextPath}/jsp/logout.jsp">Cerrar sesi&oacute;n</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/jsp/login.jsp">Iniciar sesi&oacute;n</a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="${pageContext.request.contextPath}/espaciocrud">Listar espacios</a></li>
        <c:if test="${not empty sessionScope.usuario}">
            <li><a href="${pageContext.request.contextPath}/reservacrud">Mis reservas</a></li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}/usercrud">Listar usuarios</a></li>
    </ul>
</body>
</html>