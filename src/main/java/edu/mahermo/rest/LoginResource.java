package edu.mahermo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.mahermo.dao.UserDAO;
import edu.mahermo.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    private UserDAO dao = new UserDAO();

    // Recibe el JSON { "usuario": "marc", "password": "marc" }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credenciales cred) {
        User u = dao.validarUsuario(cred.getUsuario(), cred.getPassword());
        if (u != null) {
            return Response.ok(u).build(); // Devuelve los datos de usuario
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuario o contraseña incorrectos").build();
        }
    }

    // Clase interna para mapear el JSON de las credenciales: 
    public static class Credenciales {
        private String usuario;
        @JsonProperty("contrasenya")
        private String password;
        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}