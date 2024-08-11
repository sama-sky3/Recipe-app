package com.example.recipeappproject.Retrofit

import com.example.recipeappproject.DataClasses.CategoryList
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.MealList
import com.example.recipeappproject.DataClasses.MealsByCategoryList
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoryList

    @GET("filter.php?")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsByCategoryList

    @GET("search.php?")
    suspend fun getMealByName(@Query("s") s: String): MealList

    @GET("lookup.php?")
    suspend fun getMealById(@Query("i") id: String): MealList
}