package com.gochoa.wikidex.data.remote

import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import com.gochoa.wikidex.domain.Repository
import javax.inject.Inject

class RepositoryImp @Inject() constructor(
    private val pokedexApi: PokedexApi
) : Repository {
    override suspend fun getPokemon(name: String): ApiResponseStatus<PokemonDTO> = makeNetworkCall {
        pokedexApi.getPokemon(150)
    }
}
