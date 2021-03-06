package org.acme.configurations;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryConfigSource implements ConfigSource {


    private Map<String, String> prop = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryConfigSource.class);

    public InMemoryConfigSource() {
        // se puede usar la base de datos tambien en estos casos
        LOGGER.info("***    Traer configuraciones en memoria  ***");
        this.prop.put("greetings.message", "Hello In Memory");
    }

    /**
     * darle preferencia de carga, mientra mas grande el valor mas importante es la preferencia
     */
    @Override
    public int getOrdinal() {
        return 900;
    }

    @Override
    public Map<String, String> getProperties() {
        return this.prop;
    }

    @Override
    public String getValue(String propertyName) {
        return this.prop.get(propertyName);
    }

    @Override
    public String getName() {
        return "InMemoryConfigSource";
    }
    
}
