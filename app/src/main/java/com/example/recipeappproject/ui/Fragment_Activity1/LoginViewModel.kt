package com.example.recipeappproject.ui.Fragment_Activity1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeappproject.Repo.RegisterRepository
import com.example.recipeappproject.database.RegisterDatabase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RegisterRepository = RegisterRepository(
        RegisterDatabase.getInstance(application).registerDatabaseDao
    )

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> get() = _showErrorMessage

    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    fun login() {
        val email = inputEmail.value
        val password = inputPassword.value

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            _showErrorMessage.value = "Email and password are required."
            return
        }

        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null && user.password == password) {
                _navigateToHome.value = true
            } else {
                _showErrorMessage.value = "Invalid email or password."
            }
        }
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }
}
