package com.gochoa.wikidex.data.remote.response

import com.squareup.moshi.Json

data class PokemonResponse(
    @field: Json(name = "results") val results: List<Result>,
    @field: Json(name = "weight") val weight: Int,
    @field: Json(name = "types") val types: List<Type>,
    @field: Json(name = "sprites") val sprites: Sprites,
    @field: Json(name = "height") val height: Int,
    @field: Json(name = "name") val name: String,
    @field: Json(name = "id") val id: Int
)