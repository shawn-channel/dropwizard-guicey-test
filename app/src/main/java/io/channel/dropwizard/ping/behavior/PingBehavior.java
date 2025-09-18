package io.channel.dropwizard.ping.behavior;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class PingBehavior {
    private static final String TEMPLATE = "from server started at %s, pong to ping with a message saying \"%s\"";
    private final String serverStartedAt;

    @Inject
    public PingBehavior(@Named("STARTUP_TIME") String serverStartedAt) {
        this.serverStartedAt = serverStartedAt;
    }

    public String getPongString(String message) {
        return String.format(TEMPLATE, serverStartedAt, message);
    }
    
}
