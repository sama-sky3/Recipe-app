package com.example.recipeappproject.Repo

import com.example.recipeappproject.DataClasses.RegisterEntity
import com.example.recipeappproject.dao.RegisterDatabaseDao

class RegisterRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun getUserByEmail(userEmail: String):RegisterEntity?{
        return dao.getUserByEmail(userEmail)
    }
}