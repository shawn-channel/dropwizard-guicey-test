package io.channel.dropwizard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.inject.name.Names;

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
}
