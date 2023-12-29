package com.example.rethinksugar.domain
data class Recipes(

    val id: String,

    val name: String,

    val flavor: String,

    val description: String,

    val imageUrl: String,

    val ingredients: List<String>,

    val recipe: String
)

