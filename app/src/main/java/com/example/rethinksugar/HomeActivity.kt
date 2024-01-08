package com.example.rethinksugar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rethinksugar.adapter.MainCategoryAdapter
import com.example.rethinksugar.adapter.SubCategoryAdapter
import com.example.rethinksugar.databinding.ActivityHomeBinding
import com.example.rethinksugar.firebase.Recipes
import com.example.rethinksugar.firebase.RecipesCategory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainAdapter: MainCategoryAdapter
    private lateinit var subAdapter: SubCategoryAdapter
    private  var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainAdapter = MainCategoryAdapter{selectedCategory ->
            showSubCategories(selectedCategory)
            category = selectedCategory

        }
        binding.mainCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.mainCategories.adapter = mainAdapter

        subAdapter = SubCategoryAdapter{selectedRecipe ->
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
                    if(categoryList.isNotEmpty()){
                        showSubCategories(categoryList[0].nameCategory)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    showError("Sorry, we encountered a problem connecting to the server, ${error}")
                }
            })
    }

    private fun showSubCategories(nameCategory: String){
        FirebaseDatabase.getInstance().getReference("RecipesCategories/$nameCategory/recipes")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val recipeList = arrayListOf<Recipes>()
                    for(recipe in snapshot.children){
                        val currentRecipe = recipe.getValue(Recipes::class.java)
                        currentRecipe?.let{
                            recipeList.add(it)
                        }
                    }
                    subAdapter.differ.submitList(recipeList)
                }

                override fun onCancelled(error: DatabaseError) {
                    showError("Sorry, we encountered a problem connecting to the server")
                }
            })
    }


    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
