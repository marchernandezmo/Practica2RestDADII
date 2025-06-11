<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <meta name="generator" content="Eclipse IDE" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="author" content="Marc Hern&aacute;ndez Montesinos" />
    <link
      rel="shortcut icon"
      href="${pageContext.request.contextPath}/favicon.png"
      type="image/x-icon"
    />
    <title>Iniciar sesi&oacute;n - Reserva de espacios de MaHerMo</title>
  </head>
  <body>
    <h2>Iniciar Sesi&oacute;n</h2>
    <form id="loginForm">
      <label for="usuario">Usuario:</label>
      <input type="text" id="usuario" name="usuario" required /><br /><br />

      <label for="contrasenya">Contrase&ntilde;a:</label>
      <input type="password" id="contrasenya" name="contrasenya" required/><br /><br />

      <input type="submit" value="Iniciar sesi&oacute;n" />
    </form>
    <br />
    <c:if test="${not empty error}">
      <p style="color: red">${error}</p>
    </c:if>
    <br />
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al Inicio</a>
    <script>
      document
        .getElementById("loginForm")
        .addEventListener("submit", function (e) {
          e.preventDefault();
          const usuario = document.getElementById("usuario").value;
          const contrasenya = document.getElementById("contrasenya").value;
          fetch("/practica2-marc/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ usuario, contrasenya }),
          })
            .then((resp) => {
              if (resp.ok) return resp.json();
              else throw new Error("Credenciales incorrectas");
            })
            .then((user) => {
              // Si existe el usuario, redirige a index.jsp:
              window.location.href = "index.jsp";
            })
            .catch((err) => {
              document.getElementById("error").textContent = err.message;
            });
        });
    </script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
  </body>
</html>