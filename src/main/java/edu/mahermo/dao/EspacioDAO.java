package edu.mahermo.dao;

import edu.mahermo.model.Espacio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAO {

    public List<Espacio> listarEspacios() {
        List<Espacio> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM espacios")) {
            while(rs.next()){
                Espacio espacio = new Espacio(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"));
                list.add(espacio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Métodos para crear, actualizar y eliminar espacios
    public boolean insertarEspacio(Espacio espacio) {
        boolean result = false;
        String sql = "INSERT INTO espacios(nombre, description) VALUES(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, espacio.getNombre());
            stmt.setString(2, espacio.getDescription());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean actualizarEspacio(Espacio espacio) {
        boolean result = false;
        String sql = "UPDATE espacios SET nombre = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, espacio.getNombre());
            stmt.setString(2, espacio.getDescription());
            stmt.setInt(3, espacio.getId());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean eliminarEspacio(int id) {
        boolean result = false;
        String sql = "DELETE FROM espacios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public Espacio obtenerEspacioPorId(int id) {
        Espacio espacio = null;
        String sql = "SELECT * FROM espacios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    espacio = new Espacio(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return espacio;
    }
}
