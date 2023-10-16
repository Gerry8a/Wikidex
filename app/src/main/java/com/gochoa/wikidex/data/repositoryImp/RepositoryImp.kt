package com.gochoa.wikidex.data.repositoryImp

import com.gochoa.wikidex.data.local.dao.PokemonDao
import com.gochoa.wikidex.data.local.entity.PokemonEntity
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.PokedexApi
import com.gochoa.wikidex.data.remote.makeNetworkCall
import com.gochoa.wikidex.data.remote.mapper.Mapper
import com.gochoa.wikidex.data.remote.response.Result
import com.gochoa.wikidex.domain.model.Pokemon
import com.gochoa.wikidex.domain.repository.Repository
import com.gochoa.wikidex.domain.repository.RoomRepository
import javax.inject.Inject

class RepositoryImp @Inject() constructor(
    private val pokemonDao: PokemonDao,
    private val pokedexApi: PokedexApi
) : Repository, RoomRepository {

    override suspend fun getListPokemon(limit: Int): ApiResponseStatus<List<Pokemon>> =
        makeNetworkCall {
            val response = pokedexApi.getListPokemon(limit)
            Mapper().fromListResponseToModelList(response.results)
        }

    override suspend fun getPokemon(namePokemon: String): ApiResponseStatus<Pokemon> =
        makeNetworkCall {
            val response = pokedexApi.getPokemon(namePokemon)
            Mapper().fromResponseToModel(namePokemon, response)
        }

    override suspend fun insertPokemon(pokemonEntity: PokemonEntity) =
        pokemonDao.insertPokemon(pokemonEntity)

    override suspend fun getAllPokemon(): List<Pokemon> {
        val listEntity = pokemonDao.getListPokemon()
        return Mapper().fromEntityListToModelList(listEntity)
    }
}
