<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
	<title>Gestión de Reservas</title>
  <script>
    const context = "${pageContext.request.contextPath}";
    const base    = window.location.origin + context;

    document.addEventListener("DOMContentLoaded", () => {
      const tbl    = document.getElementById("tabla-reservas");
      const form   = document.getElementById("form-reserva");
      const espI   = document.getElementById("espacioId");
      const fiI    = document.getElementById("fechaInicio");
      const ffI    = document.getElementById("fechaFin");
      const descI  = document.getElementById("description");

      function cargarReservas() {
        fetch(base + "/api/reservas")
          .then(r => r.json())
          .then(data => {
            tbl.innerHTML = data.map(r =>
              `<tr>
                <td>${r.id}</td>
                <td>${r.espacioId}</td>
                <td>${r.fechaInicio}</td>
                <td>${r.fechaFin}</td>
                <td>${r.description}</td>
                <td>
                  <button data-id="${r.id}" class="btn-edit">Editar</button>
                  <button data-id="${r.id}" class="btn-del">Borrar</button>
                </td>
              </tr>`).join("");
          });
      }

      form.addEventListener("submit", e => {
        e.preventDefault();
        const id = form.dataset.editId;
        const payload = {
          espacioId:  Number(espI.value),
          fechaInicio: fiI.value,
          fechaFin:    ffI.value,
          description: descI.value
        };
        const method = id ? "PUT" : "POST";
        const url    = base + "/api/reservas" + (id ? `/${id}` : "");

        fetch(url, {
          method,
          headers: { "Content-Type": "application/json" },
          credentials: "same-origin",
          body: JSON.stringify(payload)
        })
        .then(() => {
          form.reset();
          delete form.dataset.editId;
          cargarReservas();
        });
      });

      tbl.addEventListener("click", e => {
        if (e.target.classList.contains("btn-edit")) {
          const id = e.target.dataset.id;
          fetch(base + `/api/reservas/${id}`)
            .then(r => r.json())
            .then(r => {
              form.dataset.editId    = r.id;
              espI.value             = r.espacioId;
              fiI.value              = r.fechaInicio;
              ffI.value              = r.fechaFin;
              descI.value            = r.description;
            });
        }
        if (e.target.classList.contains("btn-del")) {
          const id = e.target.dataset.id;
          fetch(base + `/api/reservas/${id}`, {
            method: "DELETE",
            credentials: "same-origin"
          })
          .then(() => cargarReservas());
        }
      });

      cargarReservas();
    });
  </script>
</head>
<body>
  <h2>Reservas</h2>
  <table border="1">
    <thead><tr><th>ID</th><th>Espacio</th><th>Inicio</th><th>Fin</th><th>Desc.</th><th>Acciones</th></tr></thead>
    <tbody id="tabla-reservas"></tbody>
  </table>

  <h3>Crear / Editar Reserva</h3>
  <form id="form-reserva">
    <label>ID del espacio: <input type="number" id="espacioId" required/></label><br/>
    <label>Inicio: <input type="datetime-local" id="fechaInicio" required/></label><br/>
    <label>Fin:    <input type="datetime-local" id="fechaFin"    required/></label><br/>
    <label>Descripción: <input type="text" id="description" required/></label><br/>
    <button type="submit">Guardar</button>
  </form>
  <p><a href="${pageContext.request.contextPath}/index.jsp">Volver</a></p>
</body>
</html>
