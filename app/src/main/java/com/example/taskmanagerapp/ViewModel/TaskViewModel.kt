package com.example.taskmanagerapp.ViewModel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.taskmanagerapp.Model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    // Liste mutable des tâches
    private val _tasks = MutableStateFlow<List<Task>>(emptyList()) // Initialisation avec une liste vide
    val tasks = _tasks.asStateFlow() // Exposition en StateFlow immuable

    // Fonction pour mettre à jour la liste des tâches
    fun updateTaskList(loadedTasks: List<Task>) {
        _tasks.value = loadedTasks
    }

    // Fonction pour sauvegarde le prochain ID
    fun saveTaskId(context: Context, taskIdKey: String, taskId: Int) {
        val gson = Gson()
        val json = gson.toJson(taskId) // Convertir taskId en JSON
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(taskIdKey, json).apply() // Sauvegarder, - .apply() pour une sauvegarde asynchrone
    }

    // Fonction pour récupérer les ID
    fun getTaskId(context: Context, taskIdKey: String): Int {
        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(taskIdKey, null) ?: return 1
        return gson.fromJson(json, Int::class.java) // Désérialiser le JSON en entier
    }
    // Fonction pour ajouter une nouvelle tâche
    fun addTask(title: String, description: String, isCompleted: Boolean, context: Context, taskIdKey: String, taskKey: String) {
        val nextId = getTaskId(context, taskIdKey)
        val newTask = Task(nextId, title, description, isCompleted)
        _tasks.value += newTask // Ajouter une nouvelle tâche à la liste existante
        saveTaskId(context, taskIdKey, nextId + 1)
        saveTask(context, taskKey, _tasks.value)
    }

    // Fonction pour supprimer une tâche par l'ID
    fun deleteTask(taskId: Int, context: Context, taskKey: String) {
        _tasks.value = _tasks.value.filterNot { it.id == taskId } // Filtrer la tâche à supprimer
        saveTask(context, taskKey, _tasks.value)
    }

    // Fonction pour modifier une tâche par l'ID
    fun modifyTask(taskId: Int, newTitle: String, newDescription: String, isCompleted: Boolean, context: Context, taskKey: String) {
        // Trouver l'index de la tâche à modifier
        val taskIndex = _tasks.value.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            // Modifier la tâche en créant une copie avec les nouveaux champs
            _tasks.value = _tasks.value.toMutableList().apply {
                this[taskIndex] = this[taskIndex].copy(
                    title = newTitle,
                    description = newDescription,
                    isCompleted = isCompleted
                )
            }
        }
        // Sauvegarder la liste mise à jour
        saveTask(context, taskKey, _tasks.value)
    }

    // Fonction pour changer l'état (terminée ou non)
    fun updateTaskStatus(taskId: Int, context: Context, taskKey: String) {
        val updatedTasks = _tasks.value.map {
            if (it.id == taskId) {
                it.copy(isCompleted = !it.isCompleted)
            } else {
                it
            }
        }
        if (_tasks.value != updatedTasks) {
            _tasks.value = updatedTasks
            saveTask(context, taskKey, _tasks.value)
        }
    }

    // Fonction pour sauvegarde les tâches
    fun saveTask(context: Context, taskKey: String, tasks: List<Task>) {
        val gson = Gson()
        val json = gson.toJson(tasks) // Convertir liste d'objets Task en JSON
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(taskKey, json).apply() // Sauvegarder, - .apply() pour une sauvegarde asynchrone
        Log.d("TaskViewModel", "saveTask json: $json") // Ajoutez un log pour voir ce qui est sauvegardé
    }

    // Fonction pour récupérer les tâches
    fun fetchTasks(context: Context, taskKey: String) {
        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(taskKey, null)
        Log.d("TaskViewModel", "getTask json: $json") // Ajoutez un log pour voir ce qui est récupéré
        val listType = object : TypeToken<List<Task>>() {}.type
        val loadedTasks = gson.fromJson<List<Task>>(json, listType) ?: emptyList()
        _tasks.value = loadedTasks
    }
}