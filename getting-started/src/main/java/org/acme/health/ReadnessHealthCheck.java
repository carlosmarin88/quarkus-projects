package org.acme.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

//lo apago porque uso CDI
//@Readiness
public class ReadnessHealthCheck implements HealthCheck {


    @Override
    public HealthCheckResponse call() {
        
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("Greeting up");
        
        if(databaseIsUp()){
            builder.up();
        }else{
            builder.down();
            builder.withData("fallo", "Base de datos no arrancada");
        }

        return builder.build();
    }

    private boolean databaseIsUp() {
        return true;
    }
    
}
