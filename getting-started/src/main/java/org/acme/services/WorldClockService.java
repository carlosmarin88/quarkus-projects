package org.acme.services;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.dto.WorldClock;
import org.acme.headers.WorldClockHeaders;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
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
    //aca seteamos datos en el hedaer
    //@ClientHeaderParam(name = "X-Logger", value = "DEBUG")
    //Otra manera de settear header con un objeto
    WorldClock getNow(@BeanParam WorldClockHeaders wClockHeaders);
    
}
