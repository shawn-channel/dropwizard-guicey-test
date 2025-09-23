package io.channel.dropwizard.ip.resource;

import com.google.inject.Inject;

import io.channel.dropwizard.ip.behavior.IPValidateBehavior;
import io.channel.dropwizard.ip.model.IPValidateRequest;
import io.channel.dropwizard.ip.view.IPValidateView;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;

@Path("/ip")
@Slf4j
public class IPResource {
    private final IPValidateBehavior ipValidateBehavior;

    @Inject
    public IPResource(IPValidateBehavior ipValidateBehavior) {
        this.ipValidateBehavior = ipValidateBehavior;
    }

    @RequiresIPValidation
    @POST
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public IPValidateView validate(@QueryParam("office") Boolean isOffice, @Valid IPValidateRequest body) {
        log.info("office: {}, address: {}", isOffice, body.getAddress());
        return new IPValidateView(ipValidateBehavior.validate(isOffice, body.getAddress()));
    }
    
}
