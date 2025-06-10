<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Formulario de usuarios - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>Formulario de usuario</h2>
    <form action="${pageContext.request.contextPath}/usercrud" method="post">
        <!-- Campo oculto para el ID:  -->
        <input type="hidden" name="id" value="${usuarioForm != null ? usuarioForm.id : ''}" />
        
        <label for="usuario">Usuario:</label>
        <input type="text" name="usuario" id="usuario" value="${usuarioForm != null ? usuarioForm.usuario : ''}" required /><br/><br/>
        
        <label for="contrasenya">Contrase&ntilde;a:</label>
        <input type="password" name="contrasenya" id="contrasenya" value="${usuarioForm != null ? usuarioForm.contrasenya : ''}" required /><br/><br/>
        
        <label for="rol">Rol:</label>
        <select name="rol" id="rol">
            <option value="user" <c:if test="${usuarioForm != null && usuarioForm.rol eq 'user'}">selected</c:if>>User</option>
            <option value="admin" <c:if test="${usuarioForm != null && usuarioForm.rol eq 'admin'}">selected</c:if>>Admin</option>
        </select><br/><br/>
        
        <input type="submit" value="Enviar" />
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/usercrud">Volver a la lista de usuarios</a>
</body>
</html>