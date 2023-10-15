package com.gochoa.wikidex.domain

import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import com.gochoa.wikidex.data.remote.response.PokemonResponse

interface Repository {

    suspend fun getListPokemon(limit: Int): ApiResponseStatus<List<Pokemon>>
}