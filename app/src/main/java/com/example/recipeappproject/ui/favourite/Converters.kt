package com.example.recipeappproject.ui.favourite

import androidx.room.TypeConverter
import com.example.recipeappproject.DataClasses.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromMealList(meals: List<Meal>): String {
        return Gson().toJson(meals) // Convert list to JSON string
    }

    @TypeConverter
    fun toMealList(data: String): List<Meal> {
        val listType = object : TypeToken<List<Meal>>() {}.type
        return Gson().fromJson(data, listType) // Convert JSON string back to list
    }
}