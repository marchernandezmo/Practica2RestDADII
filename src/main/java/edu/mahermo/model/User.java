package edu.mahermo.model;

public class User {
    private int id;
    private String usuario;
    private String contrasenya;
    private String rol; // "admin" o "user"

    // Constructor, getters y setters
    public User() { }

    public User(int id, String usuario, String contrasenya, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasenya() {
        return contrasenya;
    }
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
