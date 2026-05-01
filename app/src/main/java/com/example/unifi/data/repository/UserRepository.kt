package com.example.unifi.data.repository

import com.example.unifi.data.model.User

class UserRepository {

    private var user: User? = null

    fun registerUser(nombre: String, correo: String, usuario: String) {
        user = User(nombre, correo, usuario)
    }

    fun getUser(): User? {
        return user
    }

    fun logout() {
        user = null
    }

    fun toggleTheme() {
        user = user?.copy(temaOscuro = !(user?.temaOscuro ?: false))
    }

    fun isDarkTheme(): Boolean {
        return user?.temaOscuro ?: false
    }
}