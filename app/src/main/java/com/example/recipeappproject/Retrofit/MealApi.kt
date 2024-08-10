package com.example.recipeappproject.Retrofit

import com.example.recipeappproject.DataClasses.CategoryList
import com.example.recipeappproject.DataClasses.MealList
import com.example.recipeappproject.DataClasses.MealsByCategory
import com.example.recipeappproject.DataClasses.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.GET

interface MealApi {
    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") category:String):Call<MealsByCategoryList>
}