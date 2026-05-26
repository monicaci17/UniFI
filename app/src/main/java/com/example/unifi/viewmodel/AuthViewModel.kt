package com.example.unifi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifi.data.model.UserProfile
import com.example.unifi.data.repository.FirebaseUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val profile: UserProfile? = null,
    val error: String? = null,
    val message: String? = null
)

class AuthViewModel(
    private val repository: FirebaseUserRepository = FirebaseUserRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState(isLoggedIn = repository.currentUser() != null))
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        loadCurrentProfile()
    }

    fun login(correo: String, password: String) {
        if (correo.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Ingresa correo y contraseña") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, message = null) }
            runCatching { repository.login(correo, password) }
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(isLoading = false, isLoggedIn = true, profile = profile, message = "Sesión iniciada")
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(isLoading = false, error = exception.message ?: "No se pudo iniciar sesión")
                    }
                }
        }
    }

    fun register(nombre: String, correo: String, password: String) {
        if (nombre.isBlank() || correo.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Completa nombre, correo y contraseña") }
            return
        }

        if (password.length < 6) {
            _uiState.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, message = null) }
            runCatching { repository.register(nombre, correo, password) }
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(isLoading = false, isLoggedIn = true, profile = profile, message = "Cuenta creada")
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(isLoading = false, error = exception.message ?: "No se pudo crear la cuenta")
                    }
                }
        }
    }

    fun loadCurrentProfile() {
        if (repository.currentUser() == null) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.getProfile() }
                .onSuccess { profile ->
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true, profile = profile) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(isLoading = false, error = exception.message) }
                }
        }
    }

    fun saveProfile(nombre: String, carrera: String, semestre: String, meta: String) {
        val current = _uiState.value.profile
        val uid = current?.uid ?: repository.currentUser()?.uid.orEmpty()
        val correo = current?.correo ?: repository.currentUser()?.email.orEmpty()

        if (uid.isBlank()) {
            _uiState.update { it.copy(error = "Primero inicia sesión") }
            return
        }

        val updatedProfile = UserProfile(
            uid = uid,
            nombre = nombre.trim(),
            correo = correo,
            carrera = carrera.trim(),
            semestre = semestre.trim(),
            meta = meta.trim()
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, message = null) }
            runCatching { repository.saveProfile(updatedProfile) }
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, profile = updatedProfile, message = "Perfil guardado en Firebase")
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(isLoading = false, error = exception.message ?: "No se pudo guardar el perfil")
                    }
                }
        }
    }

    fun logout() {
        repository.logout()
        _uiState.value = AuthUiState(isLoggedIn = false)
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, message = null) }
    }
}
