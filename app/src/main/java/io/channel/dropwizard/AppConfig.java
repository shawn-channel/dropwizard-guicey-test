package io.channel.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AppConfig extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();
    
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
    public void setDataSourceFactory(DataSourceFactory database) {
        this.database = database;
    }
}
