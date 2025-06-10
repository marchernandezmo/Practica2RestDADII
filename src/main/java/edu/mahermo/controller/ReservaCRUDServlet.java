package edu.mahermo.controller;

import edu.mahermo.dao.ReservaDAO;
import edu.mahermo.dao.EspacioDAO;
import edu.mahermo.model.Reserva;
import edu.mahermo.model.Espacio;
import edu.mahermo.model.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/reservacrud/*")
public class ReservaCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservaDAO reservaDao = new ReservaDAO();
    private EspacioDAO espacioDao = new EspacioDAO();
    
    public ReservaCRUDServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User usuario = (User) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        
        String action = request.getPathInfo();
        
        if (action == null || action.equals("/") || action.equals("/listar")) {
            List<Reserva> reservas;
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                reservas = reservaDao.listarReservas();
            } else {
                reservas = reservaDao.listarReservasPorUsuario(usuario.getId());
            }
            request.setAttribute("reservas", reservas);
            request.getRequestDispatcher("/jsp/listarreservas.jsp").forward(request, response);
            
        } else if (action.equals("/nueva")) {
            List<Espacio> espacios = espacioDao.listarEspacios();
            request.setAttribute("espacios", espacios);
            request.getRequestDispatcher("/jsp/formularioreservas.jsp").forward(request, response);
            
        } else if (action.equals("/editar")) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Reserva reserva = reservaDao.obtenerReservaPorId(id);
                    
                    if (reserva != null) {
                        if ("admin".equalsIgnoreCase(usuario.getRol()) || 
                            reserva.getUsuarioId() == usuario.getId()) {
                            
                            List<Espacio> espacios = espacioDao.listarEspacios();
                            request.setAttribute("reserva", reserva);
                            request.setAttribute("espacios", espacios);
                            request.getRequestDispatcher("/jsp/formularioreservas.jsp").forward(request, response);
                            return;
                        } else {
                            request.setAttribute("error", "No tiene permisos para editar esta reserva. ");
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/reservacrud");
            
        } else if (action.equals("/cancelar")) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    reservaDao.cancelarReserva(id, usuario.getId());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/reservacrud");
            
        } else if (action.equals("/eliminar")) {
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(idStr);
                        reservaDao.eliminarReserva(id);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/reservacrud");
            
        } else {
            response.sendRedirect(request.getContextPath() + "/reservacrud");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User usuario = (User) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        
        String id = request.getParameter("id");
        String espacioIdStr = request.getParameter("espacioId");
        String fechaHoraInicioStr = request.getParameter("fechaHoraInicio");
        String estado = request.getParameter("estado");
        
        if (estado == null || estado.trim().isEmpty()) {
            estado = "activa";
        }
        
        try {
            int espacioId = Integer.parseInt(espacioIdStr);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioStr, formatter);
            
            LocalDateTime fechaHoraFin = fechaHoraInicio.plusHours(2);
            
            Integer reservaIdExcluir = null;
            if (id != null && !id.trim().isEmpty()) {
                reservaIdExcluir = Integer.parseInt(id);
            }
            
            boolean disponible = reservaDao.verificarDisponibilidad(espacioId, fechaHoraInicio, fechaHoraFin, reservaIdExcluir);
            
            if (!disponible) {
                request.setAttribute("error", "El espacio no está disponible en la franja horaria seleccionada. ");
                List<Espacio> espacios = espacioDao.listarEspacios();
                request.setAttribute("espacios", espacios);
                
                if (id != null && !id.trim().isEmpty()) {
                    Reserva reserva = reservaDao.obtenerReservaPorId(Integer.parseInt(id));
                    request.setAttribute("reserva", reserva);
                }
                
                request.getRequestDispatcher("/jsp/formularioreservas.jsp").forward(request, response);
                return;
            }
            
            if (id == null || id.trim().isEmpty()) {
                Reserva nuevaReserva = new Reserva();
                nuevaReserva.setEspacioId(espacioId);
                nuevaReserva.setUsuarioId(usuario.getId());
                nuevaReserva.setFechaHoraInicio(fechaHoraInicio);
                nuevaReserva.setFechaHoraFin(fechaHoraFin);
                nuevaReserva.setEstado(estado);
                reservaDao.insertarReserva(nuevaReserva);
                
            } else {
                int idInt = Integer.parseInt(id);
                Reserva reservaExistente = reservaDao.obtenerReservaPorId(idInt);
                
                if (reservaExistente != null) {
                    if ("admin".equalsIgnoreCase(usuario.getRol()) || 
                        reservaExistente.getUsuarioId() == usuario.getId()) {
                        
                        Reserva reservaActualizada = new Reserva();
                        reservaActualizada.setId(idInt);
                        reservaActualizada.setEspacioId(espacioId);
                        reservaActualizada.setUsuarioId(reservaExistente.getUsuarioId());
                        reservaActualizada.setFechaHoraInicio(fechaHoraInicio);
                        reservaActualizada.setFechaHoraFin(fechaHoraFin);
                        reservaActualizada.setEstado(estado);
                        reservaDao.actualizarReserva(reservaActualizada);
                    }
                }
            }
            
        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en los datos proporcionados. ");
            List<Espacio> espacios = espacioDao.listarEspacios();
            request.setAttribute("espacios", espacios);
            request.getRequestDispatcher("/jsp/formularioreservas.jsp").forward(request, response);
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/reservacrud");
    }
}