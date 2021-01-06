package com.camarin;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;


import io.reactivex.Flowable;

@ApplicationScoped
public class ProducesData {
    
    Random random = new Random();

    // le pongo un nombre al canal
    //@Outgoing("my-in-memory")
    public Flowable<Integer> generate(){
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
                .map(tick -> random.nextInt(100));
    }
}
