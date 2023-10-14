package com.gochoa.wikidex.data.remote

import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id")  id: Int): PokemonDTO
}