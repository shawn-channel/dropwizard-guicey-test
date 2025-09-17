package io.channel.dropwizard.ping.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.channel.dropwizard.ping.view.Pong;

class PingResourceTest {

    @Test
    void getPingShouldReturnPong() {
        Assertions.assertEquals(Pong.class, new PingResource("%s", "unknown").getPing().getClass());
    }

}