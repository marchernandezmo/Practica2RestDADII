package edu.mahermo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import edu.mahermo.model.User;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se requiere inicialización especial
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Definimos las URLs públicas (es decir, las que no requieren estar autenticado): 
        String loginURI = req.getContextPath() + "/jsp/login.jsp";
        String loginServletURI = req.getContextPath() + "/login";
        String indexURI = req.getContextPath() + "/index.jsp";
        String faviconURI = req.getContextPath() + "/favicon.png";
        
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("usuario") != null);
        
        // Comprobamos si es una petición de login o acceso a archivos públicos: 
        String requestURI = req.getRequestURI();
        boolean loginRequest = requestURI.equals(loginURI) || requestURI.equals(loginServletURI)
                               || requestURI.equals(indexURI) || requestURI.equals(faviconURI);
        
        // Si el usuario ya está autenticado y solicita la página de login, se le redirige a index.jsp: 
        if (loggedIn && requestURI.equals(loginURI)) {
            res.sendRedirect(indexURI);
            return;
        }
        
        // Si la URL solicitada es para la gestión de usuarios, verificamos el rol de admin: 
        if (requestURI.startsWith(req.getContextPath() + "/usercrud")) {
            // Si no está autenticado, redirige al login: 
            if (!loggedIn) {
                res.sendRedirect(loginURI);
                return;
            }
            // Si está autenticado, comprobamos que el usuario sea administrador: 
            User usuario = (User) session.getAttribute("usuario");
            if (usuario == null || !"admin".equalsIgnoreCase(usuario.getRol())) {
            	req.getRequestDispatcher("/jsp/errorNoAdmin.jsp").forward(request, response);
                return;
            }
        }
        
        // Para el resto de URLs, si el usuario está autenticado o es una petición de login, continúa; 
        // En caso contrario, redirige al login.
        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}
