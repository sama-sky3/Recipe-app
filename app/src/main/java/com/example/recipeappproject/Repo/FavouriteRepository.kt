package com.example.recipeappproject.Repo

import android.content.Context
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.database.FavouriteDatabase

class FavouriteRepository(var context: Context) {
    private val database: FavouriteDatabase = FavouriteDatabase.getInstance(context)
    suspend fun getFavouriteMeals(userEmail: String?): List<Meal>? {
        return database.favouriteDatabaseDao.getFavouriteListByEmail(userEmail)
    }

    suspend fun addMealToFavourites(userEmail: String, meal: Meal) {
        database.favouriteDatabaseDao.addMealToFavourites(userEmail, meal)
    }

    suspend fun removeMealFromFavourites(userEmail: String, meal: Meal) {
        database.favouriteDatabaseDao.removeMealFromFavourites(userEmail, meal)
    }
}
