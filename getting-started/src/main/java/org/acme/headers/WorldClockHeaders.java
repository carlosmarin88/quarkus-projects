package org.acme.headers;

import javax.ws.rs.HeaderParam;

public class WorldClockHeaders {
    
    @HeaderParam("X-Logger")
    public String logger;
}
