package com.xeridia.resource;

import com.xeridia.model.PokemonListResponse;
import com.xeridia.model.PokemonResponse;
import com.xeridia.service.CacheService;
import com.xeridia.service.PokemonService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/users/pokemons")
@RequestScoped
public class PokemonResource {

    PokemonService pokemonService;
    CacheService cacheService;

    public PokemonResource(@RestClient PokemonService pokemonService, CacheService cacheService) {
        this.pokemonService = pokemonService;
        this.cacheService = cacheService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PokemonListResponse> list() {
        return pokemonService.getPokemons();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PokemonResponse> findById(@PathParam("id") Long id) {
        return cacheService.getPokemon(id);
    }
}
