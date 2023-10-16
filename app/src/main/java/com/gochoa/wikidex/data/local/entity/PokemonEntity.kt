package com.gochoa.wikidex.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gochoa.wikidex.utils.Dictionary.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonEntity(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "urlImage")
    var urlImage: String?,
    @ColumnInfo(name = "fistType")
    var fistType: String?,
    @ColumnInfo(name = "secondType")
    var secondType: String?,
    @ColumnInfo(name = "weight")
    var weight: Int?,
    @ColumnInfo(name = "height")
    var height: Int?,
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false,
)

