package com.example.recipeappproject.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipeappproject.DataClasses.FavouriteEntity
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.RegisterEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Dao
interface FavouriteDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    // Return a String (the JSON representation of the favourite list)
    @Query("SELECT favourite_List FROM Favourite_user_table WHERE useremail = :userEmail")
    suspend fun getFavouriteListJsonByEmail(userEmail: String?): String?

    // Convert the JSON string to a List<Meal>
    suspend fun getFavouriteListByEmail(userEmail: String?): List<Meal>? {
        val jsonString = getFavouriteListJsonByEmail(userEmail)
        return jsonString?.let {
            Gson().fromJson(it, object : TypeToken<List<Meal>>() {}.type)
        }
    }

    @Query("DELETE FROM Favourite_user_table WHERE useremail = :userEmail")
    suspend fun deleteFavouritesByEmail(userEmail: String)

    @Transaction
    suspend fun addMealToFavourites(userEmail: String, meal: Meal) {
        val existingFavourites = getFavouriteListByEmail(userEmail)
        val favouriteList = existingFavourites?.toMutableList() ?: mutableListOf()

        favouriteList.add(meal)
        val updatedFavourites = FavouriteEntity(useremail = userEmail, favouriteList = Gson().toJson(favouriteList))
        insertFavourite(updatedFavourites)
    }

    @Transaction
    suspend fun removeMealFromFavourites(userEmail: String, meal: Meal) {
        val existingFavourites = getFavouriteListByEmail(userEmail)
        existingFavourites?.let {
            val favouriteList = it.toMutableList()
            favouriteList.removeIf { existingMeal -> existingMeal.idMeal == meal.idMeal }
            val updatedFavourites = FavouriteEntity(useremail = userEmail, favouriteList = Gson().toJson(favouriteList))
            insertFavourite(updatedFavourites)
        }
    }
}
