package com.example.unifi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.User
import com.example.unifi.data.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    var isDarkTheme: Boolean by mutableStateOf(repository.isDarkTheme())
        private set

    fun register(nombre: String, correo: String, usuario: String) {
        repository.registerUser(nombre, correo, usuario)
    }

    fun getUser(): User? {
        return repository.getUser()
    }

    fun logout() {
        repository.logout()
    }

    fun toggleTheme() {
        repository.toggleTheme()
        isDarkTheme = repository.isDarkTheme()
    }
}
