package com.gochoa.wikidex.domain

import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.dto.PokemonDTO

interface Repository {

    suspend fun getPokemon(name: String): ApiResponseStatus<PokemonDTO>
}