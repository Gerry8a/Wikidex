package com.gochoa.wikidex.data.remote.dto

data class Pokemon(
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val types: List<Type>,
    val weight: Int
)