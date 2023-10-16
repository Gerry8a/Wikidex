package com.gochoa.wikidex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gochoa.wikidex.data.local.entity.PokemonEntity
import com.gochoa.wikidex.domain.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM POKEMON_TABLE")
    suspend fun getListPokemon(): List<PokemonEntity>
}