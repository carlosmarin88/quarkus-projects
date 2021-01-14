package org.acme.health;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

//lo apago porque uso CDI 
//@Liveness
public class LivenessHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("WorldClockAPI");
        
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("worldclockapi.com", 80), 1000);
            builder.up();
        } catch (IOException e) {
            builder.down();
        }

        return builder.build();
    }
    
}
