package com.example.rethinksugar.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface RepicesDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll():Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipes: RecipesEntity)
}