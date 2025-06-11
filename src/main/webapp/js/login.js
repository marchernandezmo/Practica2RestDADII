document.addEventListener("DOMContentLoaded", () => {
  console.log("DOM cargado, asignando listener al form");

  const form = document.getElementById("loginForm");
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    console.log("Formulario enviado, leyendo campos");

    const usuario    = document.getElementById("usuario").value;
    const contrasenya = document.getElementById("contrasenya").value;
    console.log("Credenciales:", usuario, contrasenya);

    fetch("/practica2-marc/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ usuario, contrasenya })
    })
    .then(resp => {
      console.log("Respuesta HTTP:", resp.status);
      if (resp.ok) return resp.json();
      throw new Error("Credenciales incorrectas");
    })
    .then(user => {
      console.log("Usuario validado:", user);
      console.log("Redirigiendo a index.jsp");
      window.location.href = "index.jsp";
    })
    .catch(err => {
      console.error("Error en login:", err);
      document.getElementById("error").textContent = err.message;
    });
  });
});
