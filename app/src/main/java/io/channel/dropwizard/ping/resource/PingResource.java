package io.channel.dropwizard.ping.resource;

import io.channel.dropwizard.ping.view.Pong;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
    @GET
    public Pong getPing() {
        return new Pong();
    }
}
