package edu.mahermo.rest;

import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class RestApp extends ResourceConfig {
    public RestApp() {
        packages("edu.mahermo.rest");
    }
}
