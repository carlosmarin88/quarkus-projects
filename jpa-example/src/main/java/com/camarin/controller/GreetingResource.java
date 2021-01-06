package com.camarin.controller;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.camarin.entity.Developer;
import com.camarin.entity.DeveloperWithPanache;
import com.camarin.repository.DeveloperRepository;

@Path("/dev")
public class GreetingResource {

    @Inject
    EntityManager em;

    @Inject
    DeveloperRepository developerRepository;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer getDev(@PathParam("id") Long id) {
        return em.find(Developer.class, id);
    }

    @GET
    @Path("/dao/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer getDevDAO(@PathParam("id") Long id) {
        return this.developerRepository.findById(id);
    }


    @GET
    @Path("/panache/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperWithPanache getDevPanache(@PathParam("id") Long id) {
        return DeveloperWithPanache.findById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDev(Developer dev){
        
        em.persist(dev);
        
        return Response.created(URI.create("/dev/" + dev.getId())).build();
    }


    @POST
    @Path("/dao")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDevDAOResponse(Developer dev){
        
        dev = this.developerRepository.create(dev);
        return Response.created(URI.create("/dev/dao/" + dev.getId())).build();
    }

    @POST
    @Path("/panache")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDevWithPanache(DeveloperWithPanache dev){
        dev.persist();
        return Response.created(URI.create("dev/panache/" + dev.id)).build();
    }

    @GET
    @Path("/panache")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeveloperWithPanache> getAllDevs(){
        return DeveloperWithPanache.findAll().list();
    }

    @GET
    @Path("panache/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperWithPanache findByName(@NotNull @PathParam("name") String name){
        return DeveloperWithPanache.find("name", name).firstResult();
    }


    @GET
    @Path("dao/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Developer findByNameDAO(@NotNull @PathParam("name") String name){
        return this.developerRepository.findByName(name);
    }
    
    @GET
    @Path("/panache/name/{name}/age/{age}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeveloperWithPanache findByNameAndAge(@NotNull @PathParam("name") String name, 
                                                @NotNull @PathParam("age")Integer age){
        return DeveloperWithPanache.find("name = ?1 and age = ?2 ", name,age).firstResult();
    }   
}