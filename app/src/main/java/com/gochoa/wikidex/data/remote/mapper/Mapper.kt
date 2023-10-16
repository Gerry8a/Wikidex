package com.gochoa.wikidex.data.remote.mapper

import com.gochoa.wikidex.data.local.entity.PokemonEntity
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
        val types = result.types.size
        if (types > 1) {
            return Pokemon(
                result.id,
                namePokemon,
                result.weight,
                result.height,
                false,
                result.sprites.front_default,
                result.types[0].type.name,
                result.types[1].type.name,
            )
        } else {
            return Pokemon(
                result.id,
                namePokemon,
                result.weight,
                result.height,
                false,
                result.sprites.front_default,
                result.types[0].type.name
            )
        }
    }

    fun fromResponseToModel(name: String, result: PokemonResponse): Pokemon {
        return fromDTOToModel(name, result)
    }

    private fun fromEntityToModel(entity: PokemonEntity): Pokemon {
        return Pokemon(
            entity.id,
            entity.name,
            entity.weight,
            entity.height,
            false,
            entity.urlImage,
            entity.fistType,
            entity?.secondType,
        )
    }

    fun fromEntityListToModelList(pokemonList: List<PokemonEntity>): List<Pokemon> {
        return pokemonList.map { fromEntityToModel(it) }
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