package com.example.recipeappproject.ui.search

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.MealList
import com.example.recipeappproject.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var searchedMealLiveData = MutableLiveData<List<Meal>>()


    fun searchMealDetail(name: String, context: Context?) {
        viewModelScope.launch {
            try {
                val mealsList = RetrofitInstance.api.getMealByName(name)
                searchedMealLiveData.value = mealsList.meals

                if (mealsList.meals.isEmpty())
                    Toast.makeText(context?.applicationContext,"No such a meal",Toast.LENGTH_SHORT
                    ).show()

            }catch (e: Exception){
                Log.e("SearchViewModel", "failed to get search results ",e )
            }
        }

    }

    fun observeSearchLiveData(): MutableLiveData<List<Meal>> {
        return searchedMealLiveData
    }
}