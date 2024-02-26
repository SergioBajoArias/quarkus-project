package com.xeridia.model;

import java.util.List;

public record PokemonListResponse(int count, String next, String previous, List<PokemonResponse> results) {

}
