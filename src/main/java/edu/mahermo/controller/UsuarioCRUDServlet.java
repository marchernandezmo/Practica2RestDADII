package edu.mahermo.controller;

import edu.mahermo.dao.UserDAO;
import edu.mahermo.model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/usercrud/*")
public class UsuarioCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao = new UserDAO();
    
    public UsuarioCRUDServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el path info para determinar la acción (listar, editar, eliminar): 
        String action = request.getPathInfo();
        
        if (action == null || action.equals("/") || action.equals("/listar")) {
            // Listar usuarios: 
            List<User> usuarios = userDao.listarUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/jsp/listarusuarios.jsp").forward(request, response);
        } else if (action.equals("/eliminar")) {
            // Eliminar usuario: Se pasa el id como parámetro (?id=3): 
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    userDao.eliminarUsuario(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/usercrud");
        } else if (action.equals("/editar")) {
            // Editar usuario: Se pasa el id como parámetro (/usercrud/editar?id=3)
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    User usuario = userDao.obtenerUsuarioPorId(id);
                    if (usuario != null) {
                        request.setAttribute("usuarioForm", usuario);
                        request.getRequestDispatcher("/jsp/formulariousuarios.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/usercrud");
        } else {
            // Acción desconocida: se redirige al listado: 
            response.sendRedirect(request.getContextPath() + "/usercrud");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Insertar o actualizar usuarios: 
        String id = request.getParameter("id");
        String usuarioStr = request.getParameter("usuario");
        String contrasenya = request.getParameter("contrasenya");
        String rol = request.getParameter("rol");
        
        // Si no se envía ID, se intenta insertar o actualizar por nombre: 
        if (id == null || id.trim().isEmpty()) {
            // Buscamos si ya existe un usuario con ese nombre (ignoramos las mayúsculas): 
            User existingUser = userDao.obtenerUsuarioPorNombreDeUsuario(usuarioStr);
            if (existingUser != null) {
                // Actualizamos el usuario existente: 
                existingUser.setContrasenya(contrasenya);
                existingUser.setRol(rol);
                userDao.actualizarUsuario(existingUser);
            } else {
                // Insertamos un nuevo usuario: 
                User newUser = new User();
                newUser.setUsuario(usuarioStr);
                newUser.setContrasenya(contrasenya);
                newUser.setRol(rol);
                userDao.insertarUsuario(newUser);
            }
        } else {
            // Si se envía un ID, se actualiza directamente ese usuario: 
            try {
                int idInt = Integer.parseInt(id);
                User updatedUser = new User(idInt, usuarioStr, contrasenya, rol);
                userDao.actualizarUsuario(updatedUser);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Redirigir al listado de usuarios: 
        response.sendRedirect(request.getContextPath() + "/usercrud");
    }
}
