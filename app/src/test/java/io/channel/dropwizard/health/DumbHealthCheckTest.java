package io.channel.dropwizard.health;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DumbHealthCheckTest {
    @Test
    void checkAlwaysReturnsHealth() throws Exception {
        Assertions.assertTrue(new DumbHealthCheck().check().isHealthy());
    }
}
