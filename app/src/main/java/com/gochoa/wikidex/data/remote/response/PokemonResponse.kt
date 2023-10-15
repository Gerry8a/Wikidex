package com.gochoa.wikidex.data.remote.response

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
){

}