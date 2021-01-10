package org.acme.dto;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.acme.annotations.NotExpired;

public class Beer {
    
    @NotNull
    @NotBlank
    private String name;

    @Min(100)
    private int capacity;

    @NotExpired
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate expired;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Beer [capacity=" + capacity + ", name=" + name + "]";
    }

    public Beer(){}

    public Beer(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }


    
}
