package com.example.rethinksugar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rethinksugar.databinding.ItemSubCategoryBinding
import com.example.rethinksugar.firebase.RecipesCategory // Import the correct class

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    var recipesList = listOf<RecipesCategory>()

    class RecipeViewHolder(private val binding: ItemSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipes: RecipesCategory) {
            binding.recipe = recipes
            binding.executePendingBindings()
        }
    }

    fun setData(data: List<RecipesCategory>) {
        recipesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.bind(recipe)
    }
}
