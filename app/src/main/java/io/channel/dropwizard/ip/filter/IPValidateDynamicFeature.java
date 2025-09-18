package io.channel.dropwizard.ip.filter;

import java.net.http.HttpHeaders;

import io.channel.dropwizard.ip.resource.IPResource;
import io.channel.dropwizard.ip.resource.RequiresIPValidation;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class IPValidateDynamicFeature implements DynamicFeature {

    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
        if (!resourceInfo.getResourceClass().equals(IPResource.class)) {
            return;
        }
        if (resourceInfo.getResourceMethod().getAnnotation(RequiresIPValidation.class) == null) {
            return;
        }

        configuration.register(new IPValidateResourceFilter());
    }

    private static class IPValidateResourceFilter implements ContainerRequestFilter {
        private static final String TARGET_PATH = "ip/validate";

        @Override
        public void filter(ContainerRequestContext requestContext) {
            if(!TARGET_PATH.equals(requestContext.getUriInfo().getPath())) {
                return;
            }
            log.info("filter 로직 동작");
        }
    }

}
