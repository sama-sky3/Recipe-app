package com.example.recipeappproject.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.Repo.FavouriteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository) : ViewModel() {

    private val _favouriteMeals = MutableLiveData<List<Meal>>()
    val favouriteMeals: LiveData<List<Meal>> get() = _favouriteMeals

    fun getFavouriteMeals(userEmail: String ?){
        viewModelScope.launch {
            _favouriteMeals.value = repository.getFavouriteMeals(userEmail)
        }
    }

    fun addMealToFavorites(userEmail: String, meal: Meal) {
        viewModelScope.launch {
            repository.addMealToFavourites(userEmail, meal)
            getFavouriteMeals(userEmail)
        }
    }

    fun removeMealFromFavorites(userEmail: String, meal: Meal) {
        viewModelScope.launch {
            repository.removeMealFromFavourites(userEmail, meal)
            getFavouriteMeals(userEmail)
        }
    }


}
