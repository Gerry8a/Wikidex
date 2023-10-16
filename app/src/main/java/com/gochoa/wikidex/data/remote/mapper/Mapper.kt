package com.gochoa.wikidex.data.remote.mapper

import com.gochoa.wikidex.data.remote.response.PokemonResponse
import com.gochoa.wikidex.data.remote.response.Result
import com.gochoa.wikidex.domain.model.Marker
import com.gochoa.wikidex.domain.model.Pokemon
import com.google.firebase.firestore.QuerySnapshot

class Mapper {

    private fun fromResultToDomain(result: Result): Pokemon {
        return Pokemon(
            null,
            result.name,
            null,
            null,
            null,
        )
    }

    fun fromListResponseToModelList(results: List<Result>): List<Pokemon> {
        return results.map { fromResultToDomain(it) }
    }

    private fun fromDTOToModel(namePokemon: String, result: PokemonResponse): Pokemon {
        return Pokemon(
            result.id,
            namePokemon,
            result.weight,
            null,
            false,
            null
        )
    }

    fun fromResponseToModel(name: String, result: PokemonResponse): Pokemon {
        return fromDTOToModel(name, result)
    }

    private fun fromSnapShotToDomain(ggg: Marker): Marker {
        val marker = Marker()
        marker.longitude = marker.longitude
        marker.latitude = marker.latitude

        return marker
    }

    fun fromSnapShotToModel(result: QuerySnapshot): List<Marker> {
        val lista = mutableListOf<Marker>()
        for (document in result) {
            val ggg = document.toObject(Marker::class.java)
            lista.add(ggg)
        }
        return lista.map { fromSnapShotToDomain(it) }
    }
}