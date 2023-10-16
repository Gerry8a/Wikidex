package com.gochoa.wikidex.data.dto

data class DTOREsponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<Type>
)