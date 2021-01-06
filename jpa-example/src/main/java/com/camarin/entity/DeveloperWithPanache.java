package com.camarin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class DeveloperWithPanache extends PanacheEntity {
    
    @Column(unique = true)
    public String name;

    @Column
    public Integer age;
    
}
