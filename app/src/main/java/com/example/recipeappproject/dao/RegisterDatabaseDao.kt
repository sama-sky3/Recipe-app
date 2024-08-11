package com.example.recipeappproject.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeappproject.DataClasses.RegisterEntity


@Dao
interface RegisterDatabaseDao {
    @Insert

    suspend fun insert(register: RegisterEntity)

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("SELECT * FROM Register_users_table WHERE user_email LIKE :userEmail")
    suspend fun getUserByEmail(userEmail: String): RegisterEntity?


}