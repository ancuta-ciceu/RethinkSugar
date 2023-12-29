package com.example.rethinksugar.firebase

import android.util.Log
import com.example.rethinksugar.domain.Recipes
import com.example.rethinksugar.domain.RecipesRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseStore : RecipesRepository {
    private val database = FirebaseDatabase.getInstance().reference.child("recipes")

    override fun getAll(): Flow<List<Recipes>> = callbackFlow {
        val listener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Recipes", "getAll", error.toException())
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val nodeValues = mutableListOf<RecipesCategory>()

                for(childSnapshots in snapshot.children){
                    val recipeNode = childSnapshots.getValue(RecipesCategory::class.java)
                    recipeNode?.let {
                        nodeValues.add(it)
                    }
                }

                val items = nodeValues.map{ recipeNode -> recipeNode.toDomainModel()}
                trySend(items)
                close()
            }
        }

        database.addListenerForSingleValueEvent(listener)
        awaitClose{ database.removeEventListener(listener)}
    }

    fun RecipesCategory.toDomainModel() : Recipes {
        return Recipes(id, name, flavor, description, imageUrl, ingredients, recipe)
    }
}

