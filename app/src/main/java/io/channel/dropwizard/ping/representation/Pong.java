package io.channel.dropwizard.ping.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pong {
    private static final String fixedPongString = "pong";

    @JsonProperty
    public String getContent() {
        return fixedPongString;
    }
}