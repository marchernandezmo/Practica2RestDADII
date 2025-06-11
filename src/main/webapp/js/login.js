console.log("login.js cargado");  // <-- punto de comprobación

document.getElementById("loginForm").addEventListener("submit", function(e) {
  e.preventDefault();
  console.log("submit capturado");  // <-- punto de comprobación

  const usuario    = document.getElementById("usuario").value;
  const contrasenya = document.getElementById("contrasenya").value;

  fetch("/practica2-marc/api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usuario, password: contrasenya })
  })
  .then(resp => {
    console.log("Status HTTP:", resp.status);
    if (resp.ok) return resp.json();
    throw new Error("Credenciales inválidas");
  })
  .then(user => {
    console.log("Usuario validado:", user);
    window.location.href = "index.jsp";
  })
  .catch(err => {
    console.error(err);
    document.getElementById("error").textContent = err.message;
  });
});