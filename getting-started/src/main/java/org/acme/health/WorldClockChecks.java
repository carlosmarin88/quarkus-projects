package org.acme.health;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class WorldClockChecks {
    
    @Produces
    @ApplicationScoped
    @Readiness
    HealthCheck readiness(){
        return () -> HealthCheckResponse.named("Greetings").up().build();
    }

    @Produces
    @ApplicationScoped
    @Liveness
    HealthCheck liveness(){
        return () -> HealthCheckResponse.named("World Clock API v2").state(isWorldClockApiUP()).build();
    }

    private boolean isWorldClockApiUP(){
        
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("worldclockapi.com", 80), 1000);
            
            return true;
        }catch(IOException e){
            return false;
        }
    }
}
