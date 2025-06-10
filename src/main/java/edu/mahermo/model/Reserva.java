package edu.mahermo.model;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private int espacioId;
    private int usuarioId;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String estado; // Los estados de la reserva los siguientes: "activa" o "cancelada". 
    
    private String nombreEspacio;
    private String nombreUsuario;

    public Reserva() { }

    public Reserva(int id, int espacioId, int usuarioId, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String estado) {
        this.id = id;
        this.espacioId = espacioId;
        this.usuarioId = usuarioId;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(int espacioId) {
        this.espacioId = espacioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}