package com.example.recipeappproject.ui.Fragment_Activity1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.DataClasses.RegisterEntity
import com.example.recipeappproject.Repo.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: RegisterRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _errorToast = MutableLiveData<Boolean>()
    val errorToast: LiveData<Boolean>
        get() = _errorToast

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            val existingUser = repository.getUserByEmail(email)
            if (existingUser != null) {
                _errorToast.value = true // User already exists
            } else {
                val newUser = RegisterEntity(userName = username, userEmail = email, password = password)
                repository.insert(newUser)
                _navigateToHome.value = true // Successful sign up
            }
        }
    }

    fun login(email: String, password: String): LiveData<RegisterEntity?> {
        val result = MutableLiveData<RegisterEntity?>()
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user?.password == password) {
                result.value = user // Successful login
            } else {
                result.value = null // Failed login
            }
        }
        return result
    }

    fun doneNavigatingToHome() {
        _navigateToHome.value = false
    }

    fun doneErrorToast() {
        _errorToast.value = false
    }
}
