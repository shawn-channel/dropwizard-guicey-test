package io.channel.dropwizard.ping.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.inject.Inject;

import io.channel.dropwizard.ping.view.Pong;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
    private final String template;

    @Inject
    public PingResource(@PingResponseTemplate String template) {
        this.template = template;
    }
    @GET
    public Pong getPing() {
        return new Pong(String.format(template, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now())));
    }
}
