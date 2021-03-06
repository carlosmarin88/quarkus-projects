package org.acme.services;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.dto.WorldClock;
import org.acme.dto.WorldClockFallback;
import org.acme.headers.WorldClockHeaders;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
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
    @Retry(maxRetries = 2, delay = 1, delayUnit = ChronoUnit.SECONDS)
    //devuelvo una respuesta predefinida despues de reintentar lo que haya configurado
    @Fallback(WorldClockFallback.class)

    //llamadas consecutivas dentro de una ventana en este caso las 4 ultimas,
    //despues analizamos el porcentaje de fallo para abrir 
    @CircuitBreaker(requestVolumeThreshold =  4, failureRatio = 0.75, delay = 1000)
    //aca seteamos datos en el hedaer
    //@ClientHeaderParam(name = "X-Logger", value = "DEBUG")
    //Otra manera de settear header con un objeto
    WorldClock getNow(@BeanParam WorldClockHeaders wClockHeaders);
    
    @Path("/json/{where}/now")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    WorldClock getNowByWhere(@PathParam("where") String where);

    @Path("/json/{where}/now")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<WorldClock> getNowByWhere2(@PathParam("where") String where);


}
