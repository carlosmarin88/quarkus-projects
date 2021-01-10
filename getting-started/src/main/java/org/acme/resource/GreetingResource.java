package org.acme.resource;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.Beer;
import org.acme.services.GreetingsService;

@Path("/api")
public class GreetingResource {
    
    @Inject
    GreetingsService greetingService;

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


}
