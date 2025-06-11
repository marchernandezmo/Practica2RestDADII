<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="generator" content="Eclipse IDE">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Marc Hern&aacute;ndez Montesinos">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.png" type="image/x-icon">
 	<title>Gestión de Espacios</title>
</head>
<body>
  <h2>Espacios</h2>
  <table border="1">
  <thead>
    <tr>
      <th>ID</th>
      <th>Nombre</th>
      <th>Descripción</th>
      <th>Acciones</th>
    </tr>
  </thead>
  <tbody id="lista-espacios">
  </tbody>
</table>

<script type="text/javascript">
  const context = "${pageContext.request.contextPath}";
  const base    = window.location.origin + context;

  document.addEventListener("DOMContentLoaded", () => {
    const tbody = document.getElementById("lista-espacios");

    // Función para cargar y listar los espacios: 
    function cargarEspacios() {
      fetch(base + "/api/espacios", { credentials: "same-origin" })
        .then(resp => resp.json())
        .then(data => {
          // Limpiar el contenido previo: 
          tbody.innerHTML = "";

          data.forEach(e => {
            const tr = document.createElement("tr");

            // ID: 
            const tdId = document.createElement("td");
            tdId.textContent = e.id;
            tr.appendChild(tdId);

            // Nombre: 
            const tdNombre = document.createElement("td");
            tdNombre.textContent = e.nombre;
            tr.appendChild(tdNombre);

            // Descripción: 
            const tdDesc = document.createElement("td");
            tdDesc.textContent = e.description;
            tr.appendChild(tdDesc);

            // Acciones: 
            const tdAcc = document.createElement("td");

            // Botón para editar un espacio: 
            const btnEdit = document.createElement("button");
            btnEdit.textContent = "Editar";
            btnEdit.dataset.id = e.id;
            btnEdit.addEventListener("click", () => editarEspacio(e.id));
            tdAcc.appendChild(btnEdit);

            // Espacio entre botones
            tdAcc.appendChild(document.createTextNode(" "));

            // Botón para borrar un espacio:
            const btnDel = document.createElement("button");
            btnDel.textContent = "Borrar";
            btnDel.dataset.id = e.id;
            btnDel.addEventListener("click", () => {
              if (confirm("¿Seguro que desea borrar el espacio " + e.nombre + "?")) {
                borrarEspacio(e.id);
              }
            });
            tdAcc.appendChild(btnDel);

            tr.appendChild(tdAcc);
            tbody.appendChild(tr);
          });
        })
        .catch(err => console.error("Error al cargar espacios:", err));
    }

    // Función para editar un espacio (carga los datos en el formulario): 
    function editarEspacio(id) {
      fetch(base + "/api/espacios/" + id, { credentials: "same-origin" })
        .then(resp => resp.json())
        .then(e => {
          document.getElementById("nombre").value = e.nombre;
          document.getElementById("description").value = e.description;
          // Guarda el ID en el formulario:
          document.getElementById("form-espacio").dataset.editId = e.id;
        });
    }

    // Función para borrar un espacio: 
    function borrarEspacio(id) {
      fetch(base + "/api/espacios/" + id, {
        method: "DELETE",
        credentials: "same-origin"
      })
      .then(() => cargarEspacios())
      .catch(err => console.error("Error al borrar:", err));
    }

    // Inicializar: 
    cargarEspacios();
  });
</script>

  <h3 id="form-title">Crear / Editar Espacio</h3>
  <form id="form-espacio">
    <label>Nombre: <input type="text" id="nombre" required/></label><br/>
    <label>Descripción: <input type="text" id="description" required/></label><br/>
    <button type="submit">Guardar</button>
  </form>
  <p><a href="${pageContext.request.contextPath}/index.jsp">Volver</a></p>
</body>
</html>