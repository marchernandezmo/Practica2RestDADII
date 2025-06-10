<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
    <title>Formulario de espacios - Reserva de espacios de MaHerMo</title>
</head>
<body>
    <h2>Formulario de Espacio</h2>
    <form action="${pageContext.request.contextPath}/espaciocrud" method="post">
        <!-- Campo oculto para el ID: -->
        <input type="hidden" name="id" value="${espacio != null ? espacio.id : ''}" />
        
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" id="nombre" value="${espacio != null ? espacio.nombre : ''}" required /><br/><br/>
        
        <label for="description">Descripci&oacute;n:</label><br/>
        <textarea name="description" id="description" rows="4" cols="50" required>
            ${espacio != null ? espacio.description : ''}
        </textarea><br/><br/>
        
        <input type="submit" value="Enviar" />
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/espaciocrud">Volver a la lista de espacios</a>
</body>
</html>
