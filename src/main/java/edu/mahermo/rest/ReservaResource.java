package edu.mahermo.rest;

import edu.mahermo.dao.ReservaDAO;
import edu.mahermo.model.Reserva;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/reservas")
public class ReservaResource {

    private ReservaDAO dao = new ReservaDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reserva> getAll() {
        return dao.listarReservas();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Reserva getById(@PathParam("id") int id) {
        return dao.obtenerReservaPorId(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearReserva(Reserva reserva) {
        dao.insertarReserva(reserva);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarReserva(@PathParam("id") int id, Reserva reserva) {
        reserva.setId(id);
        dao.actualizarReserva(reserva);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarReserva(@PathParam("id") int id) {
        dao.eliminarReserva(id);
        return Response.ok().build();
    }
}
