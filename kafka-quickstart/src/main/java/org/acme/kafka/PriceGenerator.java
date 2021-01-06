package org.acme.kafka;

import java.time.Duration;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

/**
 * A bean producing random prices every 5 second
 * the prices are written to kafka topic (prices). 
 * The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class PriceGenerator {
    
    private Random random = new Random();

    @Outgoing("generated-price")
    public Multi<Integer> generate(){
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .onOverflow().drop().map(tick->random.nextInt(100));
    }
}
