package com.gochoa.wikidex.domain.repository

import com.gochoa.wikidex.data.local.entity.PokemonEntity

interface RoomRepository {

    suspend fun insertPokemon(pokemonEntity: PokemonEntity)

}