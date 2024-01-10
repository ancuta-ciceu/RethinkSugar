package com.example.rethinksugar.firebase
import com.google.firebase.database.PropertyName
//retetele efectiv
data class Recipes(
    @PropertyName("id")
    val id: String,
    @PropertyName("name")
    val name: String,
    @PropertyName("time")
    val time: String,
    @PropertyName("imageUrl")
    val imageUrl: String,
    @PropertyName("ingredients")
    val ingredients: String,
    @PropertyName("recipe")
    val recipe: String
){
    constructor() : this( "","","", "", "","")
}

