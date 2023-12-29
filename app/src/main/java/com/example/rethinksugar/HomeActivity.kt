package com.example.rethinksugar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rethinksugar.adapter.MainCategoryAdapter
import com.example.rethinksugar.adapter.SubCategoryAdapter
import com.example.rethinksugar.database.RecipesEntity
import com.example.rethinksugar.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding


    private val mainCategoryAdapter = MainCategoryAdapter()
    private val subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this //observing LiveData

        val viewModel = HomeActivity()
        binding.viewModel = viewModel

        with(binding.mainCategories){
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mainCategoryAdapter
        }

        with(binding.subcategories) {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = subCategoryAdapter
        }

        //just for testing
        val arrMainCategory = listOf(
            RecipesEntity(1, "Chocolate"),
            RecipesEntity(2, "Sugar"),
            RecipesEntity(3, "Fruits"),
            RecipesEntity(4, "SugarFree")
        )

        val arrSubCategory = listOf(
            RecipesEntity(1, "Brownies"),
            RecipesEntity(2, "White Snow"),
            RecipesEntity(3, "Fruit Salad"),
            RecipesEntity(4, "Water")
        )

        // Update adapters with data
        mainCategoryAdapter.setData(arrMainCategory)
        subCategoryAdapter.setData(arrSubCategory)

    }
}