package com.example.rethinksugar.domain
import kotlinx.coroutines.flow.Flow
interface RecipesRepository {
    fun getAll(): Flow<List<Recipes>>
}