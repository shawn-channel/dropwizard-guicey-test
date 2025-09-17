package io.channel.dropwizard.ping.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Pong {
    private final String content;

    @JsonProperty
    public String getContent() {
        return content;
    }
}