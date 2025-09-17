package io.channel.dropwizard;

import com.google.inject.Provides;

import io.channel.dropwizard.ping.resource.PingResponseTemplate;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class Module extends DropwizardAwareModule<AppConfig> {
    @Override
    protected void configure() {
        configuration();
        environment();
        bootstrap();
    }

    @Provides
    @PingResponseTemplate
    public String providePingResponseTemplate() {
        return "pong to ping at %s";
    }
}
