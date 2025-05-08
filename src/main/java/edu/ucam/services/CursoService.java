package edu.ucam.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/curso")
public class CursoService {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getCursoActual")
	public int getCursoActual() {
		return 2024;
	}

}
