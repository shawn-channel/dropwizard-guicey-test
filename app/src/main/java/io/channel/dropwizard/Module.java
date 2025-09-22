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

        bind(String.class)
            .annotatedWith(Names.named("OFFICE_CIDR"))
            .toInstance("192.168.8.0/21");
            
        configuration();
        environment();
        bootstrap();
    }

    @Provides
    @Singleton
    public DataSource dataSource() {
        // Get the database configuration from AppConfig
        DataSourceFactory dataSourceFactory = configuration().getDataSourceFactory();
        
        // Build the managed data source
        return dataSourceFactory.build(environment().metrics(), "postgresql");
    }

    @Provides
    @Singleton 
    public DSLContext dslContext(DataSource dataSource) {
        Configuration configuration = new DefaultConfiguration()
            .set(dataSource)
            .set(SQLDialect.POSTGRES);
        return DSL.using(configuration);
    }
}
