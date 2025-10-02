package com.xeridia.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.Getter;
import org.jboss.resteasy.reactive.NoCache;

@Path("/api/users")
public class UsersResource {
    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    @NoCache
    public User me() {
        return new User(identity);
    }

    @Getter
    @JsonIgnoreProperties
    public static class User {
        private final String userName;
        User(SecurityIdentity identity) {
            this.userName = identity.getPrincipal().getName();
        }
    }
}
