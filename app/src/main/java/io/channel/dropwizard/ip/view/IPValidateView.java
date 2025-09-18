package io.channel.dropwizard.ip.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IPValidateView {
    @Getter
    @JsonProperty
    private final String content;
}
