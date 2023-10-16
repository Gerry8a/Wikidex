package com.gochoa.wikidex.domain.model

data class Pokemon(
    val id: Int?,
    val name: String?,
    val weight: Int?,
    val height: Int?,
    var isFavorite: Boolean?,
    val urlImage: String? = null,
    val fistType: String? = null,
    val secondType: String? = null

)