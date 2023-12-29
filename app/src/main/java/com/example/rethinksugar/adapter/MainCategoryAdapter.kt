package com.example.rethinksugar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rethinksugar.databinding.ItemMainCategoryBinding
import com.example.rethinksugar.domain.Recipes

class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {
    var recipesList = listOf<Recipes>()

    class RecipeViewHolder(private val binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipes) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    fun setData(data: List<Recipes>) {
        recipesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
