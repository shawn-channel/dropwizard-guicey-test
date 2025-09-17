package io.channel.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

public class DumbHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
    
}
