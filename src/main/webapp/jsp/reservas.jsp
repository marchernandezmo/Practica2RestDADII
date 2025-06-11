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
</head>
<body>
  <h2>Reservas</h2>
  <table border="1">
    <thead><tr><th>ID</th><th>Espacio</th><th>Inicio</th><th>Fin</th><th>Estado</th><th>Acciones</th></tr></thead>
    <tbody id="tabla-reservas"></tbody>
  </table>
  
  <h3>Crear / Editar Reserva</h3>
	<form id="form-reserva">
	  <label>ID del espacio:
	    <input type="number" id="espacioId" required/>
	  </label><br/>
	  <label>Inicio:
	    <input type="datetime-local" id="fechaHoraInicio" required/>
	  </label><br/>
	  <label>Fin:
	    <input type="datetime-local" id="fechaHoraFin" required/>
	  </label><br/>
	  <label>Estado:
	    <select id="estado" required>
	    	<option value="activa">Activa</option>
	    	<option value="cancelada">Cancelada</option>
	  </select>
	  </label><br/>
	  <button type="submit">Guardar</button>
	</form>
	
	<p><a href="${pageContext.request.contextPath}/index.jsp">Volver</a></p>
  
	<script type="text/javascript">
	  const base = window.location.origin + "${pageContext.request.contextPath}";
	
	  const form = document.getElementById("form-reserva");
	  const espI = document.getElementById("espacioId");
	  const fiI  = document.getElementById("fechaHoraInicio");
	  const ffI  = document.getElementById("fechaHoraFin");
	  const estI = document.getElementById("estado");
	  const tbl  = document.getElementById("tabla-reservas");
	
	  // Cargamos las reservas y rellenamos la tabla: 
	  function cargarReservas() {
	    fetch(base + "/api/reservas", { credentials: "same-origin" })
	      .then(res => res.json())
	      .then(data => {
	        if (!Array.isArray(data)) {
	          console.error("Reservas inválidas:", data);
	          return;
	        }
	        const html = data.map(r =>
	          "<tr>" +
	            "<td>" + r.id + "</td>" +
	            "<td>" + r.nombreEspacio + "</td>" +
	            "<td>" + new Date(r.fechaHoraInicio).toLocaleString() + "</td>" +
	            "<td>" + new Date(r.fechaHoraFin).toLocaleString() + "</td>" +
	            "<td>" + r.estado + "</td>" +
	            "<td>" +
	              "<button data-id=\"" + r.id + "\" class=\"btn-edit\">Editar</button> " +
	              "<button data-id=\"" + r.id + "\" class=\"btn-del\">Borrar</button>" +
	            "</td>" +
	          "</tr>"
	        ).join("");
	        tbl.innerHTML = html;
	      })
	      .catch(console.error);
	  }
	
	  // Editar o borrar una reserva: 
	  tbl.addEventListener("click", e => {
	    const id = e.target.dataset.id;
	    if (e.target.matches(".btn-edit")) {
	      fetch(base + "/api/reservas/" + id, { credentials: "same-origin" })
	        .then(res => res.json())
	        .then(r => {
	          form.dataset.editId = r.id;
	          espI.value = r.espacioId;
	          fiI.value = r.fechaHoraInicio;
	          ffI.value = r.fechaHoraFin;
	          estI.value = r.estado;
	        });
	    }
	    if (e.target.matches(".btn-del")) {
	      fetch(base + "/api/reservas/" + id, {
	        method: "DELETE", credentials: "same-origin"
	      }).then(cargarReservas);
	    }
	  });
	
	  // Crear o actualizar una reserva: 
	  form.addEventListener("submit", e => {
	    e.preventDefault();
	    const id = form.dataset.editId;
	    const payload = {
	      espacioId: Number(espI.value),
	      fechaHoraInicio: fiI.value,
	      fechaHoraFin:    ffI.value,
	      estado: estI.value
	    };
	    fetch(base + "/api/reservas" + (id ? "/" + id : ""), {
	      method: id ? "PUT" : "POST",
	      headers: { "Content-Type": "application/json" },
	      credentials: "same-origin",
	      body: JSON.stringify(payload)
	    })
	    .then(() => {
	      form.reset();
	      delete form.dataset.editId;
	      cargarReservas();
	    })
	    .catch(console.error);
	  });
	
	  document.addEventListener("DOMContentLoaded", cargarReservas);
	</script>
</body>
</html>