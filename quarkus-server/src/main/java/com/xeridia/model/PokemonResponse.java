package com.xeridia.model;

import java.util.List;

public record PokemonResponse(long id, String name, List<PokemonMoveResponse> pokemonMoveResponse) {

}
