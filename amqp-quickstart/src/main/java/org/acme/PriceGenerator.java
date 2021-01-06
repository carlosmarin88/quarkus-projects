package org.acme;

import java.time.Duration;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

/**
 * A bean prudicing random prices every 5 seconds
 * the prices are written to a AMQP queue (prices). The AMQP configuration is specified in the application configuration.
 * 
 */
@ApplicationScoped
public class PriceGenerator {
    
    private Random random = new Random();

    @Outgoing("generated-price")
    public Multi<Integer> generate(){
        System.out.println("Generate random value to queue prices");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
        .onOverflow().drop().map(tick -> random.nextInt(100));
    }
}
