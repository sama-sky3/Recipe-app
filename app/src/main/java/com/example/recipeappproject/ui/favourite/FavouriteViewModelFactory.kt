package com.example.recipeappproject.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeappproject.Repo.FavouriteRepository

class FavouriteViewModelFactory(private val favouriteRepository: FavouriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(favouriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
