package com.example.rethinksugar.firebase

import com.google.firebase.database.PropertyName

//ceea ce folosim pentru clasificare retete
data class RecipesCategory(
    @PropertyName("idCategory")
    val idCategory: String,

    @PropertyName("nameCategory")
    val nameCategory: String,

    @PropertyName("recipes")
    val repices: List<Recipes>,

    @PropertyName("imageCategory")
    val imageCategory: String
){
    constructor() : this( "","", emptyList(), "")

}

