package org.acme.security.jwt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/jwt")
/**
 * los atributos son propia de esta request y no de otro con @RequestScoped
 * para no ir pisando lo inject por llamadas paralelas
 */
@RequestScoped
public class TokenSecuredResource {


    @Inject
    @Claim(standard = Claims.sub)
    String sub;

    @Inject
    JsonWebToken token;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return this.token.getIssuer();
    }

    @Path("/sub")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sub(){
        return this.sub;
    }
}