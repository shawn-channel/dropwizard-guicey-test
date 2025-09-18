package io.channel.dropwizard.ping.resource;

import com.google.inject.Inject;

import io.channel.dropwizard.ping.behavior.PingBehavior;
import io.channel.dropwizard.ping.view.Pong;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
    private final PingBehavior pingBehavior;

    @Inject
    public PingResource(PingBehavior pingBehavior) {
        this.pingBehavior = pingBehavior;
    }
    
    @GET
    public Pong getPing() {
        return new Pong(pingBehavior.getPongString("Hi!"));
    }
}
