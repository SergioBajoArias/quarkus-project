package com.xeridia.service;

import com.xeridia.model.PokemonListResponse;
import com.xeridia.model.PokemonResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://pokeapi.co/api/v2/pokemon")
public interface PokemonService {
    @GET
    Uni<PokemonListResponse> getPokemons();

    @GET
    @Path("/{id}")
    Uni<PokemonResponse> getPokemon(@PathParam("id") Long id);
}
