package org.acme.security.jwt;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.dto.AuthRequest;
import org.acme.dto.AuthResponse;
import org.acme.dto.User;
import org.acme.security.utils.PBKDF2Encoder;
import org.acme.security.utils.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth")
public class AuthenticationREST {
    
    @Inject
    public PBKDF2Encoder passwordEncoder;

    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration")
    public Long duration;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationREST.class);

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest){
        
        User user = User.findByUsername(authRequest.username);
        if(user != null && user.password.equals(passwordEncoder.encode(authRequest.password))){
            try {
                return Response.ok(new AuthResponse(TokenUtils.generateToken(user.username,
                 user.roles, duration, issuer))).build();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return Response.status(Status.UNAUTHORIZED).build();
            }   
        }
        return Response.status(Status.UNAUTHORIZED).build();
    }  
}
