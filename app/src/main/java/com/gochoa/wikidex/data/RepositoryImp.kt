package com.gochoa.wikidex.data

import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.PokedexApi
import com.gochoa.wikidex.data.remote.makeNetworkCall
import com.gochoa.wikidex.data.remote.mapper.Mapper
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

    override suspend fun getPokemon(namePokemon: String): ApiResponseStatus<Pokemon> = makeNetworkCall {
        val response = pokedexApi.getPokemon(namePokemon)
        Mapper().fromResponseToModel(namePokemon, response)
    }
}
