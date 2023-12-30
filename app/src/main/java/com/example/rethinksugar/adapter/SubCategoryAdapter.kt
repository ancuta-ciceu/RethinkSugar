package com.example.rethinksugar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rethinksugar.databinding.ItemSubCategoryBinding
import com.example.rethinksugar.firebase.Recipes

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ItemSubCategoryBinding) : RecyclerView.ViewHolder(binding.root)
    val diffUtil = object : DiffUtil.ItemCallback<Recipes>(){
        override fun areItemsTheSame(
            oldItem: Recipes,
            newItem: Recipes
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Recipes,
            newItem: Recipes
        ): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeData = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(recipeData.recipe).into(imgDessert)
            dessert.text = recipeData.name
        }
    }
}
