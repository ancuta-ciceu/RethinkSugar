package com.example.rethinksugar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rethinksugar.firebase.FirebaseStore
import com.example.rethinksugar.domain.Recipes
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _mainCategories = MutableLiveData<List<Recipes>>()
    val mainCategories: LiveData<List<Recipes>> get() = _mainCategories

    private val _subCategories = MutableLiveData<List<Recipes>>()
    val subCategories: LiveData<List<Recipes>> get() = _subCategories

    private val firebaseStore = FirebaseStore()

    fun fetchData() {
        viewModelScope.launch {
            firebaseStore.getAll().collect { recipesList ->
                val mainCategories = recipesList.filter { /* filter main categories condition */ }
                val subCategories = recipesList.filter { /* filter sub categories condition */ }

                _mainCategories.value = mainCategories
                _subCategories.value = subCategories
            }
        }
    }
}
