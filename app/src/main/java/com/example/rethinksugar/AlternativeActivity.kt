package com.example.rethinksugar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rethinksugar.databinding.ActivityAlternativeBinding
import com.example.rethinksugar.firebase.Recipes
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class AlternativeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlternativeBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlternativeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ToolbarBackBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        database = FirebaseDatabase.getInstance().getReference("RecipesCategories").child("Healthy_Desserts").child("recipes")

        database.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipes = snapshot.children.mapNotNull{it.getValue(Recipes::class.java)}
                if (recipes.isNotEmpty()) {
                    val randomIndex = Random.nextInt(recipes.size)
                    val randomRecipe = recipes[randomIndex]

                    Glide.with(this@AlternativeActivity).load(randomRecipe.imageUrl)
                        .into(binding.imageView)

                    binding.recipeName.text = randomRecipe.name
                    binding.preparationTime.text = randomRecipe.time
                    binding.ingredients.text = randomRecipe.ingredients
                    binding.steps.text = randomRecipe.recipe
                }else {
                    // Handle the case where there are no recipes in the category
                    Toast.makeText(this@AlternativeActivity, "No recipes found in the category", Toast.LENGTH_SHORT).show()
                }
//                val imageUrl = recipeData?.child("imageUrl")?.getValue(String::class.java)
//
//                Glide.with(this@AlternativeActivity).load(imageUrl).into(binding.imageView)
//
//                binding.recipeName.text = recipeData?.child("name")?.getValue(String::class.java)
//                binding.preparationTime.text = recipeData?.child("time")?.getValue(String::class.java)
//                binding.ingredients.text = recipeData?.child("ingredients")?.getValue(String::class.java)
//                binding.steps.text = recipeData?.child("recipe")?.getValue(String::class.java)
            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("RecipeActivity", "Error fetching category data from Firebase: $error")
                Toast.makeText(this@AlternativeActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }

        })
    }
}