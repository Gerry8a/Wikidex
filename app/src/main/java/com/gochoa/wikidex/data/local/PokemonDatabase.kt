package com.gochoa.wikidex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gochoa.wikidex.data.local.dao.PokemonDao
import com.gochoa.wikidex.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemoDao(): PokemonDao
}