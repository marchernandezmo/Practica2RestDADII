package edu.mahermo.rest;

import edu.mahermo.dao.EspacioDAO;
import edu.mahermo.model.Espacio;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/espacios")
public class EspacioResource {

    private EspacioDAO dao = new EspacioDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Espacio> getAll() {
        return dao.listarEspacios();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Espacio getById(@PathParam("id") int id) {
        return dao.obtenerEspacioPorId(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearEspacio(Espacio espacio) {
        dao.insertarEspacio(espacio);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarEspacio(@PathParam("id") int id, Espacio espacio) {
        espacio.setId(id);
        dao.actualizarEspacio(espacio);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarEspacio(@PathParam("id") int id) {
        dao.eliminarEspacio(id);
        return Response.ok().build();
    }
}
