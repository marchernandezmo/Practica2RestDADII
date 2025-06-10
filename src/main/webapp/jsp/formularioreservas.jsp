<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="generator" content="Eclipse IDE">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Marc Hern&aacute;ndez Montesinos">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Formulario de reservas - Reserva de espacios de MaHerMo</title>
    <style>
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { padding: 8px; width: 300px; }
        .error { color: red; margin-bottom: 15px; }
        .info { color: blue; margin-bottom: 15px; }
    </style>
</head>
<body>
    <h2>
        <c:choose>
            <c:when test="${reserva != null}">Editar Reserva</c:when>
            <c:otherwise>Nueva Reserva</c:otherwise>
        </c:choose>
    </h2>
    
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    
    <div class="info">
        <strong>Nota:</strong> Las reservas son por tramos de 2 horas. 
        Seleccione la fecha y hora de inicio. La hora de fin se calculará automáticamente. 
    </div>
    
    <form action="${pageContext.request.contextPath}/reservacrud" method="post">
        <input type="hidden" name="id" value="${reserva != null ? reserva.id : ''}" />
        
        <div class="form-group">
            <label for="espacioId">Espacio:</label>
            <select name="espacioId" id="espacioId" required>
                <option value="">Seleccione un espacio</option>
                <c:forEach var="espacio" items="${espacios}">
                    <option value="${espacio.id}" 
                            <c:if test="${reserva != null && reserva.espacioId eq espacio.id}">selected</c:if>>
                        ${espacio.nombre} - ${espacio.description}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
            <label for="fechaHoraInicio">Fecha y Hora de Inicio:</label>
            <input type="datetime-local" name="fechaHoraInicio" id="fechaHoraInicio" 
                   <c:if test="${reserva != null}">
                       value="${reserva.fechaHoraInicio.toString().substring(0, 16)}"
                   </c:if>
                   required />
        </div>
        
        <c:if test="${sessionScope.usuario.rol eq 'admin' and reserva != null}">
            <div class="form-group">
                <label for="estado">Estado:</label>
                <select name="estado" id="estado">
                    <option value="activa" <c:if test="${reserva.estado eq 'activa'}">selected</c:if>>Activa</option>
                    <option value="cancelada" <c:if test="${reserva.estado eq 'cancelada'}">selected</c:if>>Cancelada</option>
                </select>
            </div>
        </c:if>
        
        <div class="form-group">
            <input type="submit" value="Guardar Reserva" />
        </div>
    </form>
    
    <br/>
    <a href="${pageContext.request.contextPath}/reservacrud">Volver al listado de reservas</a>
    
    <script>
        // Establece como fecha mínima la fecha actual: 
        document.addEventListener('DOMContentLoaded', function() {
            var now = new Date();
            now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
            document.getElementById('fechaHoraInicio').min = now.toISOString().slice(0, 16);
        });
    </script>
</body>
</html>