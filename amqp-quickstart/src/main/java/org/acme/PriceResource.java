package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

/**
 * A simple resource retrieving the "in-memory" "my-data-stream" and sending the items as 
 * server-sent events.
 */
@Path("/prices")
public class PriceResource {
    
    @Inject
    @Channel("my-data-stream")
    Publisher<Double> prices;// injects the my-data-stream channel using the @Channel qualifier 

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "hello";
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)// indicates that the content is sent using Server Sent Events
    public Publisher<Double> stream(){ // returns the stream (Reactive Stream)
        return prices;        
    }
}
