package com.example.rethinksugar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rethinksugar.database.converter.CategoryListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "categoryitems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)
    var categoryitems: List<CategoryItems>? = null
)
