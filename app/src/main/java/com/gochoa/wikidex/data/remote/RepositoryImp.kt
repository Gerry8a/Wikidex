package com.gochoa.wikidex.data.remote

import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import com.gochoa.wikidex.data.remote.mapper.Mapper
import com.gochoa.wikidex.data.remote.response.PokemonResponse
import com.gochoa.wikidex.domain.Pokemon
import com.gochoa.wikidex.domain.Repository
import javax.inject.Inject

class RepositoryImp @Inject() constructor(
    private val pokedexApi: PokedexApi
) : Repository {
    override suspend fun getListPokemon(limit: Int): ApiResponseStatus<List<Pokemon>> =
        makeNetworkCall {
            val response = pokedexApi.getListPokemon(limit)
            Mapper().fromListResponseToModelList(response.results)
        }
}
