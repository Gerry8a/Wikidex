package com.gochoa.wikidex.data.remote

import com.gochoa.wikidex.data.remote.dto.PokemonDTO
import com.gochoa.wikidex.data.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name")  name: String): PokemonResponse


    @GET("pokemon")
    suspend fun getListPokemon(@Query("limit")  limit: Int): PokemonResponse
}