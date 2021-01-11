package org.acme.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.Beer;
import org.acme.dto.WorldClock;
import org.acme.headers.WorldClockHeaders;
import org.acme.services.GreetingsService;
import org.acme.services.WorldClockService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api")
public class GreetingResource {
    
    @Inject
    GreetingsService greetingService;

    @RestClient
	@Inject
    WorldClockService worldClockService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/msg")
    public String getMsg(){
        return this.greetingService.toUpperCase();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/beer")
    public Beer getBeer(){
        return new Beer("Quilmes", 300);
    }

    @POST
    @Path("/beer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBeer(@Valid Beer beer){
        System.out.println(beer);
        return Response.ok().build();

    }

    @GET
    @Path("/now")
    @Produces(MediaType.APPLICATION_JSON)
    public WorldClock getNow(){

        WorldClockHeaders worldClockHeaders = new WorldClockHeaders();
        worldClockHeaders.logger = "DEBUG";

        return this.worldClockService.getNow(worldClockHeaders);
    }


}
