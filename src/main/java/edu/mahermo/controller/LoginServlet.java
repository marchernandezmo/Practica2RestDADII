package edu.mahermo.controller;
import edu.mahermo.dao.UserDAO;
import edu.mahermo.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String contrasenya = request.getParameter("contrasenya");

        UserDAO userDao = new UserDAO();
        User user = userDao.obtenerUsuarioPorNombreDeUsuario(usuario);

        if (user != null && user.getContrasenya().equals(contrasenya)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", user);
            // Redirigir a la página principal según el rol
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
