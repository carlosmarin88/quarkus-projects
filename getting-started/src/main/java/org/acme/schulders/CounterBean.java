package org.acme.schulders;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class CounterBean {
    
    private AtomicInteger atomicInteger = new AtomicInteger();

    @Scheduled(every = "{increment.every}")
    public void print(){
        System.out.println(atomicInteger.incrementAndGet());
    }
}
