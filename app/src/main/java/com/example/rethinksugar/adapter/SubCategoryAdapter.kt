package com.example.rethinksugar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rethinksugar.R
import com.example.rethinksugar.database.RecipesEntity
import com.example.rethinksugar.databinding.ItemSubCategoryBinding

class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    var arrSubCategory = listOf<RecipesEntity>()
    class RecipeViewHolder(private val binding: ItemSubCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: RecipesEntity) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    fun setData(arrData : List<RecipesEntity>){
        arrSubCategory = arrData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = arrSubCategory[position]
        holder.bind(recipe)
    }
}