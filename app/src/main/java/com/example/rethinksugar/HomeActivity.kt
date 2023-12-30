package com.example.rethinksugar

import android.os.Bundle
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

class HomeActivity : AppCompatActivity() { // Change the superclass to FragmentActivity
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainAdapter: MainCategoryAdapter
    private lateinit var subAdapter: SubCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainAdapter = MainCategoryAdapter()
        binding.mainCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.mainCategories.adapter = mainAdapter

        subAdapter = SubCategoryAdapter()
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
                    val cakesCategoryId = "Cakes"
                    showSubCategories(cakesCategoryId)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled event
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
                    TODO("Not yet implemented")
                }
            })
    }
}
