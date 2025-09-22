package io.channel.dropwizard;

import io.dropwizard.core.Configuration;

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
