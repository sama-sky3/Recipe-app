package com.example.recipeappproject.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite_user_table")
data class FavouriteEntity(
    @PrimaryKey
    var useremail: String,

    @ColumnInfo(name = "favourite_List")
    var favouriteList: String // Store as JSON string
)
