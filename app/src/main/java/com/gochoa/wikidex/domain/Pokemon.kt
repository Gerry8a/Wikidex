package com.gochoa.wikidex.domain

data class Pokemon(
    val id: Int?,
    val name: String?,
    val order: Int?,
    val weight: Int?,
    var isFavorite: Boolean?
)