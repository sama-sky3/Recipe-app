package com.example.recipeappproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeappproject.dao.RecipeDao
import com.example.recipeappproject.entities.Recipes

@Database(entities = [Recipes::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    companion object {
        private var recipesDatabase: RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase {
            if (recipesDatabase != null) {
                recipesDatabase = Room.databaseBuilder(
                    context, RecipeDatabase::class.java,
                    name = "recipe.dp"
                ).build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao(): RecipeDao
}