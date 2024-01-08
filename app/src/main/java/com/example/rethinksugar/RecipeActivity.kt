package com.example.rethinksugar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rethinksugar.databinding.ActivityRecipeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var category: String
    private lateinit var recipeName: String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category_name") ?: ""
        Log.d("RecipeActivity", "Categoria selectata: $category")
        recipeName = intent.getStringExtra("recipe_name") ?: ""
        Log.d("RecipeActivity", "Reteta selectata: $recipeName")

        binding.ToolbarBackBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        database = FirebaseDatabase.getInstance().getReference("RecipesCategories").child(category).child("recipes")
            .child("Chocolate_Cake")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("RecipeActivity", "snapshot: $snapshot")

                val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)
                Glide.with(this@RecipeActivity).load(imageUrl).into(binding.imageView)

                binding.recipeName.text = snapshot.child("name").getValue(String::class.java)
                binding.preparationTime.text = snapshot.child("time").getValue(String::class.java)
                binding.ingredients.text = snapshot.child("ingredients").getValue(String::class.java)
                binding.steps.text = snapshot.child("recipe").getValue(String::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RecipeActivity", "Error fetching category data from Firebase: $error")
                Toast.makeText(this@RecipeActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
