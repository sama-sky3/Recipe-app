package com.example.recipeappproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeappproject.DataClasses.FavouriteEntity
import com.example.recipeappproject.dao.FavouriteDatabaseDao


@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract val favouriteDatabaseDao: FavouriteDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: FavouriteDatabase? = null


        fun getInstance(context: Context): FavouriteDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteDatabase::class.java,
                        "user_favourites_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}