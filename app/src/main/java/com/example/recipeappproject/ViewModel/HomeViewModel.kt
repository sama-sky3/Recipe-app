package com.example.recipeappproject.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.recipeappproject.DataClasses.*
import com.example.recipeappproject.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class HomeViewModel : ViewModel() {
    private val mutableCategory = MutableLiveData<List<Category>>()
    private val mutableMeal = MutableLiveData<List<MealsByCategory>>()

    init {
        getAllCategories()

    }

     fun getAllCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { CategoryList ->
                    mutableCategory.postValue(CategoryList.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home View Model Categories", t.message.toString())
            }
        })
    }
    fun observeCategories(): LiveData<List<Category>> {
        return mutableCategory
    }

    fun getMealsByCategory(category:String){
        RetrofitInstance.api.getMealsByCategory(category).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.isSuccessful) {
                    val mealsList = response.body()
                    Log.d("HomeViewModel", "Meals for $category: ${mealsList?.meals}")
                    mutableMeal.postValue(mealsList?.meals)
                } else {
                    Log.d("HomeViewModel", "Failed to get meals: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Home View Model Recipes",t.message.toString())
            }

        })
    }

    fun observeMeal():LiveData<List<MealsByCategory>>{
        return mutableMeal
    }
}
