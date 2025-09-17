package io.channel.dropwizard.ping.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.channel.dropwizard.ping.representation.Pong;

class PingResourceTest {

    @Test
    void getPingShouldReturnPong() {
        Assertions.assertEquals(Pong.class, new PingResource().getPing().getClass());
    }

}