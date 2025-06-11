package edu.mahermo.filter;

import java.io.IOException;

import edu.mahermo.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se requiere inicialización especial
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String context    = req.getContextPath();        // ej. "/practica2-marc"
        String requestURI = req.getRequestURI();         // ej. "/practica2-marc/api/login"

        // 1) EXCLUSIÓN: deja pasar TODO /api/*
        if (requestURI.startsWith(context + "/api/")) {
            chain.doFilter(request, response);
            return;
        }

        // Resto de tu lógica actual:
        String loginJsp    = context + "/jsp/login.jsp";
        String loginPath   = context + "/login";
        String indexPage   = context + "/index.jsp";
        String favicon     = context + "/favicon.png";

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("usuario") != null;

        boolean publicRequest = requestURI.equals(loginJsp)
                             || requestURI.equals(loginPath)
                             || requestURI.equals(indexPage)
                             || requestURI.equals(favicon);

        if (loggedIn && requestURI.equals(loginJsp)) {
            res.sendRedirect(indexPage);
            return;
        }

        // Control de /usercrud…
        if (requestURI.startsWith(context + "/usercrud")) {
            if (!loggedIn) {
                res.sendRedirect(loginJsp);
                return;
            }
            User u = (User) session.getAttribute("usuario");
            if (u == null || !"admin".equalsIgnoreCase(u.getRol())) {
                req.getRequestDispatcher("/jsp/errorNoAdmin.jsp").forward(request, response);
                return;
            }
        }

        if (loggedIn || publicRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginJsp);
        }
    }

    @Override
    public void destroy() {
    }
}
