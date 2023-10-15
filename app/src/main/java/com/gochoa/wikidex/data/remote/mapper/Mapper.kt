package com.gochoa.wikidex.data.remote.mapper

import com.gochoa.wikidex.data.remote.response.PokemonResponse
import com.gochoa.wikidex.data.remote.response.Result
import com.gochoa.wikidex.domain.Pokemon

class Mapper {

    private fun fromResultToDomain(result: Result): Pokemon{
        return Pokemon(
             null,
            result.name,
            null,
            null,
            null,
        )
    }

    fun fromListResponseToModelList(results: List<Result>): List<Pokemon>{
        return results.map {fromResultToDomain(it) }
    }

    private fun fromDTOToModel(namePokemon: String, result: PokemonResponse): Pokemon{
        return Pokemon(
            result.id,
            namePokemon,
            result.weight,
            null,
            false,
            null
        )
    }

    fun fromResponseToModel(name: String, result: PokemonResponse): Pokemon{
        return fromDTOToModel(name, result)
    }
}