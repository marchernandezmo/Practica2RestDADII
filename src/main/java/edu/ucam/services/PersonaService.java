package edu.ucam.services;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import edu.ucam.entity.Persona;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/persona")
public class PersonaService {
		
	public static List<Persona> personaList = new ArrayList<Persona>();
	
	@GET
	@Path("/getDatosPersona")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDameDatosPersona() {
		return "HOLA...";
	}
	
	@GET
	@Path("/getSaludo/{nombre}/{apellido}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludoConParametros(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido) {
		return "Bienvenido Sr/a: " + nombre + ", " + apellido;
	}
	
	
	@GET
	@Path("/get-persona/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersona(@PathParam("id") int idPersona) {
		JSONObject respuestaPersona = new JSONObject();
		Persona personaEncontrada = null;
		
		for(int i=0; i < personaList.size() && personaEncontrada == null; i++) {
			if(personaList.get(i).getId() == idPersona)
				personaEncontrada = personaList.get(i);
		}
		
		JSONObject personaJSON = new JSONObject();
		if(personaEncontrada != null) {
			personaJSON.put("id", personaEncontrada.getId());
			personaJSON.put("nombre", personaEncontrada.getNombre());
			personaJSON.put("apellido1", personaEncontrada.getApellido1());
			personaJSON.put("apellido2", personaEncontrada.getApellido1());
			return Response.status(200).entity(personaJSON.toString()).build();
		} else {
			return Response.status(404).entity(personaJSON.toString()).build();
		}
				
	}
	
	@GET
	@Path("/todos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dameTodosLosUsuarios() {
		JSONObject respuestaPersonas = new JSONObject();
		
		
		for(Persona persona:personaList) {
			JSONObject personaJSON = new JSONObject();
			personaJSON.put("id", persona.getId());
			personaJSON.put("nombre", persona.getNombre());
			personaJSON.put("apellido1", persona.getApellido1());
			personaJSON.put("apellido2", persona.getApellido1());
			respuestaPersonas.append("personas", personaJSON);
		}
		
		return Response.status(200).entity(respuestaPersonas.toString()).build();
	}
	
	
	@POST	
	@Path("/alta-usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(InputStream incomingData) {
		System.out.println("en el metodo de alta de usuario");
		
		//Recuperamos el String correspondiente al JSON que nos envia el navegador
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
			
		
		//Construimos un objeto JSON en base al recibido como cadena 
		//así es más fácil recuperar los valores
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		Persona persona = new Persona();
		if(jsonRecibido.has("id")) {
			persona.setId(jsonRecibido.getInt("id"));
			System.out.println("Hemos recibido Id");
		}
		persona.setNombre(jsonRecibido.getString("nombre"));
		persona.setApellido1(jsonRecibido.getString("apellido1"));
		persona.setApellido2(jsonRecibido.getString("apellido2"));
		personaList.add(persona);
		
		
		//Generamos un objeto JSON como respuesta
		JSONObject jsonRespuesta = new JSONObject();
		
		JSONObject userJSON = new JSONObject();
		
		userJSON.put("id", persona.getId());
		userJSON.put("nombre", persona.getNombre());
		userJSON.put("apellido1", persona.getApellido1());
		userJSON.put("apellido2", persona.getApellido2());
		
		//Se añade el JSON  a la respuesta
		jsonRespuesta.put("persona", userJSON);
		
		return Response.status(201).entity(jsonRespuesta.toString()).build();
	}
	
	@DELETE
	@Path("/borra/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response borraUsuario(@PathParam("id") int id) {
		System.out.println("Tenemos que borrar al usuario con id:" + id);
		Persona personaEncontrada = null;
		for(int i=0; i < personaList.size() && personaEncontrada == null; i++) {
			if(personaList.get(i).getId() == id)
				personaEncontrada = personaList.get(i);
		}
		if(personaEncontrada == null)
			return Response.status(409).entity("error").build();
		else {
			personaList.remove(personaEncontrada);
			return Response.status(201).entity(true).build();
		}
	}
	

}
