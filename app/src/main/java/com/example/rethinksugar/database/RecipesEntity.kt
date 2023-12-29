package com.example.rethinksugar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Recipes")
data class RecipesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    @ColumnInfo(name = "dessertName")
    var dessertName:String
):Serializable
