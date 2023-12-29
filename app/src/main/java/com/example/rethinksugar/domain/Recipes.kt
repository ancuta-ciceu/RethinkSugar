package com.example.rethinksugar.domain
import com.google.firebase.database.PropertyName
data class Recipes(
    @PropertyName("id")
    var id: String,
    @PropertyName("name")
    var name: String,
    @PropertyName("flavor")
    var flavor: String,
    @PropertyName("description")
    var description: String,
    @PropertyName("imageUrl")
    var imageUrl: String,
    @PropertyName("ingredients")
    var ingredients: List<String>,
    @PropertyName("recipe")
    var recipe: String
)

