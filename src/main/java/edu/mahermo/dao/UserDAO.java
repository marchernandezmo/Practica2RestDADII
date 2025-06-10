package edu.mahermo.dao;

import edu.mahermo.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User obtenerUsuarioPorNombreDeUsuario(String usuario) {
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE usuario = ?")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                user = new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("contrasenya"), rs.getString("rol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> listarUsuarios() {
        List<User> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while(rs.next()){
                User user = new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("contrasenya"), rs.getString("rol"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Métodos para crear, actualizar y eliminar usuarios
    public boolean insertarUsuario(User user) {
        boolean result = false;
        String sql = "INSERT INTO usuarios(usuario, contrasenya, rol) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsuario());
            stmt.setString(2, user.getContrasenya());
            stmt.setString(3, user.getRol());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean actualizarUsuario(User user) {
        boolean result = false;
        String sql = "UPDATE usuarios SET usuario = ?, contrasenya = ?, rol = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsuario());
            stmt.setString(2, user.getContrasenya());
            stmt.setString(3, user.getRol());
            stmt.setInt(4, user.getId());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean eliminarUsuario(int id) {
        boolean result = false;
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public User obtenerUsuarioPorId(int id) {
        User user = null;
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), 
                                    rs.getString("usuario"), 
                                    rs.getString("contrasenya"), 
                                    rs.getString("rol"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}