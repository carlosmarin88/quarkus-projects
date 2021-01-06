package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Broadcast;

/**
 * A bean consuming data from the "prices" AMQP queue and applying some conversion
 */
@ApplicationScoped
public class PriceConverter {
    
    private static final double CONVERSION_RATE = 0.88;

    @Incoming("prices")// indicates that the method consumes the items from the prices channel
    @Outgoing("my-data-stream")// indicates that the objects returned by the method are sent 
    //                            to the my-data-stream channel
    @Broadcast// indicates that the item are dispatched to all subscribers
    public double process(int priceInUsd){
        System.out.println("consuming random value generated by Price generator: " + priceInUsd);
        return priceInUsd * CONVERSION_RATE;
    }
}
