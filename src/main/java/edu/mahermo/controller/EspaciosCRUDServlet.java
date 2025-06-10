package edu.mahermo.controller;

import edu.mahermo.dao.EspacioDAO;
import edu.mahermo.model.Espacio;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/espaciocrud/*")
public class EspaciosCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EspacioDAO spaceDao = new EspacioDAO();
    
    public EspaciosCRUDServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el path info, que contiene la acción (/listar, /editar o /eliminar): 
        String action = request.getPathInfo();
        if (action == null || action.equals("/") || action.equals("/listar")) {
            // Listar espacios
            List<Espacio> espacios = spaceDao.listarEspacios();
            request.setAttribute("espacios", espacios);
            request.getRequestDispatcher("/jsp/listarespacios.jsp").forward(request, response);
        } else if (action.equals("/eliminar")) {
            // Eliminar espacio: el id viene como parámetro (?id=2): 
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    spaceDao.eliminarEspacio(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/espaciocrud");
        } else if (action.equals("/editar")) {
            // Editar espacio: el id en la URL (/espaciocrud/editar?id=2)
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Espacio espacio = spaceDao.obtenerEspacioPorId(id);
                    if (espacio != null) {
                        request.setAttribute("espacio", espacio);
                        request.getRequestDispatcher("/jsp/formularioespacios.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            // Si hay algún problema, redirigimos al listado: 
            response.sendRedirect(request.getContextPath() + "/espaciocrud");
        } else {
            // Acción desconocida: redirigimos al listado
            response.sendRedirect(request.getContextPath() + "/espaciocrud");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Para insertar o actualizar espacios: 
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String description = request.getParameter("description");
        
        // Si el id está vacío, se trata de un insert o de un update por nombre: 
        if (id == null || id.trim().isEmpty()) {
            // Buscamos si ya existe un espacio con el mismo nombre (se ignoran las mayúsculas): 
            List<Espacio> espacios = spaceDao.listarEspacios();
            Espacio existingSpace = null;
            for (Espacio e : espacios) {
                if (e.getNombre().equalsIgnoreCase(nombre)) {
                    existingSpace = e;
                    break;
                }
            }
            if (existingSpace != null) {
                // Actualizamos el espacio existente: 
                existingSpace.setDescription(description);
                spaceDao.actualizarEspacio(existingSpace);
            } else {
                // Insertamos un nuevo espacio: 
                Espacio nuevoEspacio = new Espacio();
                nuevoEspacio.setNombre(nombre);
                nuevoEspacio.setDescription(description);
                spaceDao.insertarEspacio(nuevoEspacio);
            }
        } else {
            // Actualización directa: se envía el id: 
            try {
                int idInt = Integer.parseInt(id);
                Espacio updatedSpace = new Espacio(idInt, nombre, description);
                spaceDao.actualizarEspacio(updatedSpace);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Tras insertar o actualizar, redirigimos al listado: 
        response.sendRedirect(request.getContextPath() + "/espaciocrud");
    }
}
