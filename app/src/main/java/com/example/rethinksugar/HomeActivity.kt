package com.example.rethinksugar

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rethinksugar.adapter.MainCategoryAdapter
import com.example.rethinksugar.adapter.SubCategoryAdapter
import com.example.rethinksugar.databinding.ActivityHomeBinding
import com.example.rethinksugar.domain.Recipes
import com.example.rethinksugar.firebase.RecipesCategory


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val mainCategoryAdapter = MainCategoryAdapter()
    private val subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        with(binding.mainCategories) {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mainCategoryAdapter
        }

        with(binding.subcategories) {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = subCategoryAdapter
        }

        viewModel.fetchData()
        viewModel.mainCategories.observe(this, { mainCategories ->
            Log.d("HomeActivity", "Main Categories: $mainCategories")
        })
        viewModel.subCategories.observe(this, { subCategories ->
            Log.d("HomeActivity", "Sub Categories: $subCategories")
        })

        val mainCategory = listOf(
            Recipes("1", "Chocolate", "Milk Chocolate", "", "https://img.taste.com.au/-l6L5bYJ/w720-h480-cfill-q80/taste/2016/11/homemade-chocolate-cake-85524-1.jpeg", listOf(""), ""),
            Recipes("2", "Vanilla", "Vanilla", "", "https://img.taste.com.au/-l6L5bYJ/w720-h480-cfill-q80/taste/2016/11/homemade-chocolate-cake-85524-1.jpeg", listOf(""), "")

        )

        val subCategory = listOf(
            RecipesCategory( "1", "Chocolate", "Milk Chocolate", "cake with milk and chocolate", "https://img.taste.com.au/-l6L5bYJ/w720-h480-cfill-q80/taste/2016/11/homemade-chocolate-cake-85524-1.jpeg", listOf("Milk","Chocolate"), "bake everithing"),
            RecipesCategory( "1", "Chocolate", "Milk Chocolate", "cake with milk and chocolate", "https://img.taste.com.au/-l6L5bYJ/w720-h480-cfill-q80/taste/2016/11/homemade-chocolate-cake-85524-1.jpeg", listOf("Milk","Chocolate"), "bake everithing")
        )

        mainCategoryAdapter.setData(mainCategory)
        subCategoryAdapter.setData(subCategory)
    }
}

