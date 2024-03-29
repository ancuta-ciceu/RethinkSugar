package com.example.rethinksugar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rethinksugar.databinding.ItemMainCategoryBinding
import com.example.rethinksugar.firebase.RecipesCategory


class MainCategoryAdapter (private val onMainCategoryClick : (RecipesCategory) -> Unit): RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root)
    val diffUtil = object : DiffUtil.ItemCallback<RecipesCategory>(){
        override fun areItemsTheSame(
            oldItem: RecipesCategory,
            newItem: RecipesCategory
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: RecipesCategory,
            newItem: RecipesCategory
        ): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeData = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(recipeData.imageCategory).into(imgCategory)
            categoryName.text = recipeData.nameCategory
            root.setOnClickListener{
                onMainCategoryClick.invoke(recipeData)
                Log.d("HomeAct", "S-a apasat pe categoria: $recipeData")

            }
        }
    }
}
