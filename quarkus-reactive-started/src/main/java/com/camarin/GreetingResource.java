package com.camarin;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.vertx.mutiny.core.Vertx;

@Path("/hello")
public class GreetingResource {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Inject
    @Channel("my-in-memory")
    Emitter<Integer> emiiter;

    @Inject
    Vertx vertx;

    @GET
    @Path("/emit/{dato}")
    public void emit(@PathParam("dato") Integer dato){
        emiiter.send(dato);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> hello() {
        // cuando llegue el request crea otro thread donde se va a procesar y despues devolver la respuesta
        // y el thread principal del pool es liberado
         return ReactiveStreams.of("h","e", "l", "l","o").map(String::toUpperCase)
         .toList()
         .run()
         .thenApply(list -> list.toString());
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> publish(){
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
        .map(s -> atomicInteger.getAndIncrement())
        .map(i ->{
            System.out.println(i);
            return Integer.toString(i);
        } );
    }
}