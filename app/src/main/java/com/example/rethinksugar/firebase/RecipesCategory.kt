package com.example.rethinksugar.firebase
import com.google.firebase.database.PropertyName
data class RecipesCategory(
    @PropertyName("id")
    val id: String,
    @PropertyName("name")
    val name: String,
    @PropertyName("flavor")
    val flavor: String,
    @PropertyName("description")
    val description: String,
    @PropertyName("imageUrl")
    val imageUrl: String,
    @PropertyName("ingredients")
    val ingredients: List<String>,
    @PropertyName("recipe")
    val recipe: String
){
    constructor() : this( "","","","","", emptyList(),"")
}

