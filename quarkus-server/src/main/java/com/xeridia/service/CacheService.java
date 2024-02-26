package com.xeridia.service;

import com.xeridia.model.PokemonResponse;
import io.quarkus.cache.CacheResult;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CacheService {

    PokemonService pokemonService;

    public CacheService(@RestClient PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @CacheResult(cacheName = "pokemon-cache")
    public Uni<PokemonResponse> getPokemon(@PathParam("id") Long id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return pokemonService.getPokemon(id);
    }
}
