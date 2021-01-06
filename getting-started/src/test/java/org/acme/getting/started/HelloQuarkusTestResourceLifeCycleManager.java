package org.acme.getting.started;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class HelloQuarkusTestResourceLifeCycleManager implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {

        Map<String, String> config = new HashMap<>();
        config.put("greetings.message", "Aloha Test");
        System.out.println("Se van a ejecutar los tests");
        return config;
    }

    @Override
    public void stop() {
        System.out.println("Ya se han ejecutado los tests");

    }
    
}
