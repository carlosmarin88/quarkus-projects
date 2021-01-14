package org.acme.dto;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

public class WorldClockFallback implements FallbackHandler<WorldClock> {

    @Override
    public WorldClock handle(ExecutionContext arg0) {
        
        WorldClock worldClock = new WorldClock();
        worldClock.setCurrentDateTime("No time");
        
        return worldClock;
    }
    

}
