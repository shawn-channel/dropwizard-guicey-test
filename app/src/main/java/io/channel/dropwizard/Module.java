package io.channel.dropwizard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.inject.Provides;
import com.google.inject.name.Names;

import io.channel.dropwizard.ping.resource.PingResponseTemplate;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class Module extends DropwizardAwareModule<AppConfig> {
    @Override
    protected void configure() {
        bind(String.class)
            .annotatedWith(Names.named("STARTUP_TIME"))
            .toInstance(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
        configuration();
        environment();
        bootstrap();
    }

    @Provides
    @PingResponseTemplate
    public String providePingResponseTemplate() {
        return "from server started at %s, pong to ping received at %s";
    }
}
