<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Iniciar sesi&oacute;n - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>Iniciar Sesi&oacute;n</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="usuario">Usuario:</label>
        <input type="text" id="usuario" name="usuario" required /><br/><br/>
        
        <label for="contrasenya">Contrase&ntilde;a:</label>
        <input type="password" id="contrasenya" name="contrasenya" required /><br/><br/>
        
        <input type="submit" value="Iniciar sesi&oacute;n" />
    </form>
    <br/>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
    <br/>
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al Inicio</a>
</body>
</html>
