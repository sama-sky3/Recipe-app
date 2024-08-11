package com.example.recipeappproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.DataClasses.Category
import com.example.recipeappproject.DataClasses.MealsByCategory
import com.example.recipeappproject.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    private val mutableCategory = MutableLiveData<List<Category>>()
    private val mutableMeal = MutableLiveData<List<MealsByCategory>>()

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            try {
                val categoryList = RetrofitInstance.api.getCategories()
                mutableCategory.value = categoryList.categories
            } catch (e: Exception) {
                Log.e("HomeViewModel", "failed to get categories", e)
            }
        }
    }

    fun observeCategories(): LiveData<List<Category>> {
        return mutableCategory
    }

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val mealsByCategory = RetrofitInstance.api.getMealsByCategory(category)
                mutableMeal.value = mealsByCategory.meals
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to get meals", e)
            }
        }
    }

    fun observeMeal(): LiveData<List<MealsByCategory>> {
        return mutableMeal
    }
}
