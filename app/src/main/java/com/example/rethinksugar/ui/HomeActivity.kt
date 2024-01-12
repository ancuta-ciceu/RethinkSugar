package com.example.rethinksugar.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rethinksugar.adapter.MainCategoryAdapter
import com.example.rethinksugar.adapter.SubCategoryAdapter
import com.example.rethinksugar.auth.LoginActivity
import com.example.rethinksugar.databinding.ActivityHomeBinding
import com.example.rethinksugar.firebase.Recipes
import com.example.rethinksugar.firebase.RecipesCategory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainAdapter: MainCategoryAdapter
    private lateinit var subAdapter: SubCategoryAdapter
    private var category: String = ""
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        mainAdapter = MainCategoryAdapter { selectedCategory ->
            showRecipes(selectedCategory)

            category = selectedCategory.nameCategory

        }
        binding.mainCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.mainCategories.adapter = mainAdapter

        subAdapter = SubCategoryAdapter { selectedRecipe ->
            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("category_name", category)
            intent.putExtra("recipe_name", selectedRecipe.name)
            Log.d("HomeActivity", "Recipe name:  ${selectedRecipe.name}")
            startActivity(intent)
        }
        binding.subCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.subCategories.adapter = subAdapter

        showAllCategories()
    }

    private fun showAllCategories() {
        FirebaseDatabase.getInstance().getReference("RecipesCategories")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoryList = arrayListOf<RecipesCategory>()
                    for (categories in snapshot.children) {
                        val currentCategory = categories.getValue(RecipesCategory::class.java)
                        currentCategory?.let {
                            categoryList.add(it)
                        }
                    }


                    mainAdapter.differ.submitList(categoryList)
                }

                override fun onCancelled(error: DatabaseError) {
                    showError("Sorry, we encountered a problem connecting to the server, ${error}")
                }
            })
    }

    private fun showRecipes(selectedCategory: RecipesCategory) {
        val nameCategory = selectedCategory.nameCategory
        Log.d("HomeActivity", "Fetching recipes for category: $nameCategory")

        FirebaseDatabase.getInstance().getReference("RecipesCategories/$nameCategory/recipes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("HomeActivity", "Snapshot for $nameCategory: $snapshot")

                    val recipeList = mutableListOf<Recipes>()
                    for (recipeSnapshot in snapshot.children) {
                        val currentRecipe = recipeSnapshot.getValue(Recipes::class.java)
                        currentRecipe?.let {
                            recipeList.add(it)
                        }
                    }
                    Log.d("HomeActivity", "Fetched recipes for category $nameCategory: $recipeList")

                    subAdapter.differ.submitList(recipeList)
                    if (recipeList.isEmpty()) {
                        Log.d("HomeActivity", "No recipes found for category $nameCategory")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HomeActivity", "Error fetching recipes: ${error.message}")
                }
            })
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
