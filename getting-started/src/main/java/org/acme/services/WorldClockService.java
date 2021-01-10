package org.acme.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.dto.WorldClock;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


/**
 * registrando un cliente rest
 */
@Path("/api")
@RegisterRestClient
public interface WorldClockService {
    
    @GET
    @Path("json/cet/now")
    @Produces(MediaType.APPLICATION_JSON)
    WorldClock getNow();
}
