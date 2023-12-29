package com.example.rethinksugar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rethinksugar.database.converter.CategoryListConverter

@Database(entities = [RecipesEntity::class, Category::class, CategoryItems::class, CategoryListConverter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao():RepicesDao
}