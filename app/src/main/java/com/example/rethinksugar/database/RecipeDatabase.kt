package com.example.rethinksugar.database

import android.content.Context
import androidx.room.Room


object RecipeDatabase{
        private var recipeDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            if(recipeDatabase != null){
                recipeDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "recipe.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return recipeDatabase!!
        }

}


