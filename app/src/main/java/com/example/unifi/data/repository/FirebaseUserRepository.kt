package com.example.unifi.data.repository

import com.example.unifi.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection = firestore.collection("usuarios")

    fun currentUser() = auth.currentUser

    suspend fun login(correo: String, password: String): UserProfile {
        val result = auth.signInWithEmailAndPassword(correo.trim(), password).await()
        val uid = result.user?.uid ?: error("No se pudo obtener el usuario")
        return getProfile(uid) ?: UserProfile(
            uid = uid,
            correo = result.user?.email.orEmpty(),
            nombre = result.user?.displayName.orEmpty()
        )
    }

    suspend fun register(nombre: String, correo: String, password: String): UserProfile {
        val result = auth.createUserWithEmailAndPassword(correo.trim(), password).await()
        val uid = result.user?.uid ?: error("No se pudo crear el usuario")
        val profile = UserProfile(uid = uid, nombre = nombre.trim(), correo = correo.trim())
        saveProfile(profile)
        return profile
    }

    suspend fun getProfile(uid: String = auth.currentUser?.uid ?: ""): UserProfile? {
        if (uid.isBlank()) return null
        val document = usersCollection.document(uid).get().await()
        return document.toObject(UserProfile::class.java)?.copy(uid = uid)
    }

    suspend fun saveProfile(profile: UserProfile) {
        val uid = profile.uid.ifBlank { auth.currentUser?.uid.orEmpty() }
        if (uid.isBlank()) error("No hay usuario activo")
        usersCollection.document(uid).set(profile.copy(uid = uid)).await()
    }

    fun logout() {
        auth.signOut()
    }
}
