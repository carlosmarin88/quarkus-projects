package org;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

// como el singleton de spring
@ApplicationScoped
public class GreetingsService {
    


    //funciona como el @Value de spring
    @ConfigProperty(name = "greetings.message")
    private String msg;


    public String toUpperCase(){
        return msg.toLowerCase();
    }
}
