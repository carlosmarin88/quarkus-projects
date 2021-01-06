package com.camarin.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.camarin.entity.Developer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class DeveloperRepository implements PanacheRepository<Developer> {

    @Transactional
    public Developer create(Developer developer) {
        persist(developer);
        return developer;
    }

    public Developer findByName(String name){
        return find("name", name).firstResult();
    }
}
