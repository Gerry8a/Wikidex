package com.gochoa.wikidex.domain.repository

import com.gochoa.wikidex.data.local.entity.PokemonEntity
import com.gochoa.wikidex.data.remote.response.Result
import com.gochoa.wikidex.domain.model.Pokemon

interface RoomRepository {

    suspend fun insertPokemon(pokemonEntity: PokemonEntity)
    suspend fun getAllPokemon(): List<Pokemon>

}