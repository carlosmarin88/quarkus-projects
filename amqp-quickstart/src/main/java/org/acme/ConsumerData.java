package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Consumer data
 */
@ApplicationScoped
public class ConsumerData {
    
    //name of the queue to consume
    @Incoming("temperature")
    public void printRandom(int randomNumber){
        System.out.println(randomNumber);
    }
}
