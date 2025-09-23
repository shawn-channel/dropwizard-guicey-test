package io.channel.dropwizard.user.resource;

import io.channel.dropwizard.user.behavior.UserBehavior;
import io.channel.dropwizard.user.model.UserContent;

import com.google.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST API resource for user operations
 * Demonstrates JOOQ custom type mapping in action
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private final UserBehavior userBehavior;
    
    @Inject
    public UserResource(UserBehavior userBehavior) {
        this.userBehavior = userBehavior;
    }
    
    /**
     * GET /user?id={userId}
     * 
     * This endpoint demonstrates JOOQ custom type mapping:
     * 1. Database stores content as JSONB
     * 2. JOOQ converts JSONB to UserContent object using manual conversion
     * 3. Jersey automatically serializes UserContent to JSON response
     */
    @GET
    public UserContent getUser(@QueryParam("id") String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        return userBehavior.getUserContent(userId)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
    
    
}
