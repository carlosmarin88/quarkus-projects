package org.acme.resource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.Beer;
import org.acme.dto.WorldClock;
import org.acme.headers.WorldClockHeaders;
import org.acme.services.GreetingsService;
import org.acme.services.WorldClockService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.opentracing.Tracer;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.ReactiveMailer;

@Path("/api")
public class GreetingResource {
    
    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;

    private Long maxTemperature = 50L;



    @Inject
    GreetingsService greetingService;

    @RestClient
	@Inject
    WorldClockService worldClockService;

    @Inject
    Tracer tracer;

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
        this.tracer.activeSpan().setBaggageItem("beer", beer.toString());
        System.out.println(beer);
        return Response.ok().build();

    }

    @GET
    @Path("/now")
    @Produces(MediaType.APPLICATION_JSON)
    //medir el tiempo de las llamadas al servicio
    @Timed(name = "CheckTimeGetNow", 
    description = "Time to get current time", unit = MetricUnits.SECONDS)
    @Counted(name = "Name of Get Time", description = "Numbers of calls")
    public WorldClock getNow(){

        WorldClockHeaders worldClockHeaders = new WorldClockHeaders();
        worldClockHeaders.logger = "DEBUG";
        WorldClock now = this.worldClockService.getNow(worldClockHeaders);

        this.mailer.send(Mail.withText("carlosmarin151288@gmail.com", "Han consultado el tiempo",
         String.format("El tiempo consultado es %s ", now.getCurrentDateTime())));
        
        return now;
    }

    @GET
    @Path("/now-2")
    @Produces(MediaType.APPLICATION_JSON)
    public WorldClock getNow2(){
        return ClientBuilder.newClient()
        .target("http://worldclockapi.com")
        .path("api/json/cet/now").request()
        .get(WorldClock.class);
    }

    @GET
    @Path("/now/{where}")
    @Produces(MediaType.APPLICATION_JSON)
    public WorldClock getNowByWhere(@PathParam("where") String where){
        return this.worldClockService.getNowByWhere(where);
    }

    @GET
    @Path("/times")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorldClock> getTimes(){
        
        WorldClock cet = worldClockService.getNowByWhere("cet");
        WorldClock gmt = worldClockService.getNowByWhere("gmt");

        return Arrays.asList(cet,gmt);
    }

    @GET
    @Path("/times-2")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<List<WorldClock>> getTimes2(){
        
        //corren los dos en paralelo y ejecuta la accion una vez los dos resuelto
        CompletionStage<WorldClock> cet = worldClockService.getNowByWhere2("cet");
       
        return cet.thenCombineAsync(worldClockService.getNowByWhere2("gmt"),
        (cetResult, gmtResult)-> Arrays.asList(cetResult,gmtResult) );
    }

    @Gauge(name = "MaxTemp", description = "Max Temperature", unit = MetricUnits.NONE)
    public Long geMaxTemp(){
        return maxTemperature;
    }



}
