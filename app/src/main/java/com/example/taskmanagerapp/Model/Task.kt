package com.example.taskmanagerapp.Model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)