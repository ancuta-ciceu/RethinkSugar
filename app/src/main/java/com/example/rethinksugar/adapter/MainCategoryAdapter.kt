package com.example.rethinksugar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rethinksugar.database.RecipesEntity
import com.example.rethinksugar.databinding.ItemMainCategoryBinding

class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {
    var arrMainCategory = listOf<RecipesEntity>()
    class RecipeViewHolder(private val binding: ItemMainCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipesEntity) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    fun setData(arrData : List<RecipesEntity>){
        arrMainCategory = arrData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = arrMainCategory[position]
        holder.bind(recipe)
    }
}