document.addEventListener("DOMContentLoaded", () => {
    fetch("/practica2-marc/api/espacios")
        .then(response => response.json())
        .then(data => {
            const lista = document.getElementById("lista-espacios");
            lista.innerHTML = "";
            data.forEach(espacio => {
                const li = document.createElement("li");
                li.textContent = `${espacio.nombre} (${espacio.descripcion})`;
                lista.appendChild(li);
            });
        })
        .catch(err => console.error("Error al obtener espacios:", err));
});
