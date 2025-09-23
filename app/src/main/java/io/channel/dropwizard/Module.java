package io.channel.dropwizard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import io.dropwizard.db.DataSourceFactory;
import io.channel.dropwizard.user.behavior.UserBehavior;
import io.channel.dropwizard.user.repository.MemberRepository;
import io.channel.dropwizard.user.resource.UserResource;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.tools.LoggerListener;

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
            
        // User module components for JOOQ custom type mapping example
        bind(MemberRepository.class);
        bind(UserBehavior.class);
        bind(UserResource.class);
            
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
            .set(SQLDialect.POSTGRES)
            // Enable SQL logging with jOOQ's LoggerListener
            .set(new LoggerListener());
        return new DefaultDSLContext(configuration);
    }
}
