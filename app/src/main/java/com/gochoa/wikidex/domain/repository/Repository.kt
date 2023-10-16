package com.gochoa.wikidex.domain.repository

import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.domain.model.Pokemon

interface Repository {
    suspend fun getListPokemon(limit: Int): ApiResponseStatus<List<Pokemon>>
    suspend fun getPokemon(name: String): ApiResponseStatus<Pokemon>
}