package com.camarin;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class ConsumerData {
    
    @Incoming("my-in-memory")
    public void printRandom(int randomNumber){
        System.out.println(randomNumber);
    }
}
