<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="generator" content="Eclipse IDE">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Marc Hern&aacute;ndez Montesinos">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Listar reservas - Reserva de espacios de MaHerMo</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .estado-activa { color: green; font-weight: bold; }
        .estado-cancelada { color: red; font-weight: bold; }
        .acciones a { margin-right: 10px; }
    </style>
</head>
<body>
    <h2>Listar reservas</h2>
    
    <a href="${pageContext.request.contextPath}/reservacrud/nueva">Nueva Reserva</a>
    <br/><br/>
    
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
    
    <table>
        <tr>
            <th>ID</th>
            <th>Espacio</th>
            <th>Usuario</th>
            <th>Fecha y hora de inicio</th>
            <th>Fecha y hora de finalización</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="reserva" items="${reservas}">
            <tr>
                <td>${reserva.id}</td>
                <td>${reserva.nombreEspacio}</td>
                <td>${reserva.nombreUsuario}</td>
                <td>
                    <c:set var="fechaInicio" value="${reserva.fechaHoraInicio.toString()}" />
                    <c:set var="fechaFormateada" value="${fechaInicio.substring(8,10)}/${fechaInicio.substring(5,7)}/${fechaInicio.substring(0,4)} ${fechaInicio.substring(11,16)}" />
                    ${fechaFormateada}
                </td>
                <td>
                    <c:set var="fechaFin" value="${reserva.fechaHoraFin.toString()}" />
                    <c:set var="fechaFinFormateada" value="${fechaFin.substring(8,10)}/${fechaFin.substring(5,7)}/${fechaFin.substring(0,4)} ${fechaFin.substring(11,16)}" />
                    ${fechaFinFormateada}
                </td>
                <td>
                    <span class="estado-${reserva.estado}">${reserva.estado}</span>
                </td>
                <td class="acciones">
                    <c:if test="${sessionScope.usuario.rol eq 'admin' or reserva.usuarioId eq sessionScope.usuario.id}">
                        <a href="${pageContext.request.contextPath}/reservacrud/editar?id=${reserva.id}">Editar</a>
                    </c:if>
                    
                    <c:if test="${sessionScope.usuario.rol eq 'admin'}">
                        <a href="${pageContext.request.contextPath}/reservacrud/eliminar?id=${reserva.id}" 
                           onclick="return confirm('¿Está seguro de que desea eliminar esta reserva?');">Eliminar</a>
                    </c:if>
                    
                    <c:if test="${reserva.usuarioId eq sessionScope.usuario.id and reserva.estado eq 'activa'}">
                        <a href="${pageContext.request.contextPath}/reservacrud/cancelar?id=${reserva.id}" 
                           onclick="return confirm('¿Está seguro de que desea cancelar esta reserva?');">Cancelar</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <c:if test="${empty reservas}">
        <p>No existen reservas con el usuario iniciado. </p>
    </c:if>
    
    <br/>
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a>
</body>
</html>