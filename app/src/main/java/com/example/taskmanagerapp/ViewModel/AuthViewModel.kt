package com.example.taskmanagerapp.ViewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class AuthViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")


    // Fonction pour l'authentification
    fun authenticate(email: String, password: String, context: Context) {
        // Identifiants hardcodées pour la connexion
        val hardcodedEmail = "user@example.com"
        val hardcodedPassword = "password123"

        // Vérification des informations de connexion
        if (email == hardcodedEmail && password == hardcodedPassword) {
            isLoggedIn = true
            errorMessage = "" // Réinitialisation du message d'erreur
            saveLoginState(context , "loginKey", true) // Sauvegarde de l'état de connexion
        } else {
            isLoggedIn = false
            errorMessage = "Email ou mot de passe incorrect" // Affiche message d'erreur
        }
    }

    //Fonction pour sauvegarder l’état de connexion
    fun saveLoginState(context: Context, loginKey: String, isLoggedIn: Boolean) {
        val gson = Gson()
        val json = gson.toJson(isLoggedIn) // Convertir le booléen en JSON
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE) // Accéder aux SharedPreferences de l'application
        sharedPreferences.edit().putString(loginKey, json).apply() // Sauvegarder l'état de connexion - .apply() pour une sauvegarde asynchrone
    }

    // Fonction pour récupérer l'état de connexion
    fun getLoginState(context: Context, loginKey: String): Boolean {
        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(loginKey, "false") // Récupérer le JSON
        return gson.fromJson(json, Boolean::class.java) // Convertir le JSON en booléen et le retourner
    }

    //Fonction de déconnexion
    fun logout(context: Context, loginKey: String) {
        isLoggedIn = false
        saveLoginState(context, loginKey, false) // Sauvegarde de l'état de déconnexion
    }
}