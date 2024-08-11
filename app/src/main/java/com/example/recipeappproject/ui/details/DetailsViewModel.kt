package com.example.recipeappproject.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.DataClasses.Category
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.MealsByCategory
import com.example.recipeappproject.Retrofit.MealApi
import com.example.recipeappproject.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class DetailsViewModel(val id:String):ViewModel() {

    private val _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal> get() = _meal

    init {
        viewModelScope.launch {
            try {
                Log.i("DetailsViewModel", "fetching meal $id ")
                _meal.value = RetrofitInstance.api.getMealById(id).meals[0]
            }catch (e: Exception){
                Log.e("DetailsViewModel", "failed to fetch meals",e )
            }
        }
    }
}