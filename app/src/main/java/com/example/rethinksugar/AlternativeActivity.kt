package com.example.rethinksugar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private var counter = 0
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlternativeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ToolbarBackBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("ChooseHealthy", Context.MODE_PRIVATE)
        counter = sharedPreferences.getInt("counter", 0)

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
                    Toast.makeText(this@AlternativeActivity, "No recipes found in the category", Toast.LENGTH_SHORT).show()
                }

            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("RecipeActivity", "Error fetching category data from Firebase: $error")
                Toast.makeText(this@AlternativeActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }

        })

        binding.counter.setOnClickListener {
            counter++
            showCounter()

        }
    }

    private fun showCounter() {
        val editor = sharedPreferences.edit()
        editor.putInt("counter", counter)
        editor.apply()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Congratulations!")
        builder.setMessage("Times you chose the healthiest option: $counter")
        builder.setPositiveButton("OK"){
            dialog, which -> dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}