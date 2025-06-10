package edu.mahermo.dao;

import edu.mahermo.model.Reserva;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public List<Reserva> listarReservas() {
        List<Reserva> list = new ArrayList<>();
        String sql = "SELECT r.*, e.nombre as nombre_espacio, u.usuario as nombre_usuario FROM reservas r JOIN espacios e ON r.espacio_id = e.id JOIN usuarios u ON r.usuario_id = u.id ORDER BY r.fecha_hora_inicio ASC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setEspacioId(rs.getInt("espacio_id"));
                reserva.setUsuarioId(rs.getInt("usuario_id"));
                reserva.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                reserva.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());
                reserva.setEstado(rs.getString("estado"));
                reserva.setNombreEspacio(rs.getString("nombre_espacio"));
                reserva.setNombreUsuario(rs.getString("nombre_usuario"));
                list.add(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reserva> listarReservasPorUsuario(int usuarioId) {
        List<Reserva> list = new ArrayList<>();
        String sql = "SELECT r.*, e.nombre as nombre_espacio, u.usuario as nombre_usuario FROM reservas r JOIN espacios e ON r.espacio_id = e.id JOIN usuarios u ON r.usuario_id = u.id WHERE r.usuario_id = ? ORDER BY r.fecha_hora_inicio ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Reserva reserva = new Reserva();
                    reserva.setId(rs.getInt("id"));
                    reserva.setEspacioId(rs.getInt("espacio_id"));
                    reserva.setUsuarioId(rs.getInt("usuario_id"));
                    reserva.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                    reserva.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());
                    reserva.setEstado(rs.getString("estado"));
                    reserva.setNombreEspacio(rs.getString("nombre_espacio"));
                    reserva.setNombreUsuario(rs.getString("nombre_usuario"));
                    list.add(reserva);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertarReserva(Reserva reserva) {
        boolean result = false;
        String sql = "INSERT INTO reservas(espacio_id, usuario_id, fecha_hora_inicio, fecha_hora_fin, estado) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, reserva.getEspacioId());
            stmt.setInt(2, reserva.getUsuarioId());
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getFechaHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getFechaHoraFin()));
            stmt.setString(5, reserva.getEstado());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean actualizarReserva(Reserva reserva) {
        boolean result = false;
        String sql = "UPDATE reservas SET espacio_id = ?, usuario_id = ?, fecha_hora_inicio = ?, fecha_hora_fin = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, reserva.getEspacioId());
            stmt.setInt(2, reserva.getUsuarioId());
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getFechaHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getFechaHoraFin()));
            stmt.setString(5, reserva.getEstado());
            stmt.setInt(6, reserva.getId());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean eliminarReserva(int id) {
        boolean result = false;
        String sql = "DELETE FROM reservas WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean cancelarReserva(int id, int usuarioId) {
        boolean result = false;
        String sql = "UPDATE reservas SET estado = 'cancelada' WHERE id = ? AND usuario_id = ? AND estado = 'activa'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.setInt(2, usuarioId);
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Reserva obtenerReservaPorId(int id) {
        Reserva reserva = null;
        String sql = "SELECT r.*, e.nombre as nombre_espacio, u.usuario as nombre_usuario FROM reservas r JOIN espacios e ON r.espacio_id = e.id JOIN usuarios u ON r.usuario_id = u.id WHERE r.id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    reserva = new Reserva();
                    reserva.setId(rs.getInt("id"));
                    reserva.setEspacioId(rs.getInt("espacio_id"));
                    reserva.setUsuarioId(rs.getInt("usuario_id"));
                    reserva.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                    reserva.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());
                    reserva.setEstado(rs.getString("estado"));
                    reserva.setNombreEspacio(rs.getString("nombre_espacio"));
                    reserva.setNombreUsuario(rs.getString("nombre_usuario"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserva;
    }

    public boolean verificarDisponibilidad(int espacioId, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Integer reservaIdExcluir) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE espacio_id = ? AND estado = 'activa' AND ((fecha_hora_inicio < ? AND fecha_hora_fin > ?) OR (fecha_hora_inicio < ? AND fecha_hora_fin > ?) OR (fecha_hora_inicio >= ? AND fecha_hora_fin <= ?))";
        
        if (reservaIdExcluir != null) {
            sql += " AND id != ?";
        }
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, espacioId);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHoraFin));
            stmt.setTimestamp(3, Timestamp.valueOf(fechaHoraInicio));
            stmt.setTimestamp(4, Timestamp.valueOf(fechaHoraFin));
            stmt.setTimestamp(5, Timestamp.valueOf(fechaHoraFin));
            stmt.setTimestamp(6, Timestamp.valueOf(fechaHoraInicio));
            stmt.setTimestamp(7, Timestamp.valueOf(fechaHoraFin));
            
            if (reservaIdExcluir != null) {
                stmt.setInt(8, reservaIdExcluir);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}