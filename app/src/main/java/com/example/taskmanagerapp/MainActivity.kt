package com.example.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanagerapp.View.HomeScreen
import com.example.taskmanagerapp.View.LoginScreen
import com.example.taskmanagerapp.View.ModifyTaskScreen
import com.example.taskmanagerapp.View.NewTaskScreen
import com.example.taskmanagerapp.View.TaskDetailsScreen
import com.example.taskmanagerapp.View.TaskListScreen
import com.example.taskmanagerapp.ViewModel.AuthViewModel
import com.example.taskmanagerapp.ViewModel.TaskViewModel
import com.example.taskmanagerapp.ui.theme.TaskManagerAppTheme

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels() // ViewModel pour l'authentification
    private val taskViewModel: TaskViewModel by viewModels() // ViewModel pour les tâc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationApp(authViewModel = authViewModel, taskViewModel = taskViewModel)
        }
    }
}

@Composable
fun NavigationApp(authViewModel: AuthViewModel, taskViewModel: TaskViewModel) {
    val navController = rememberNavController() // Contrôleur de navigation

    // Contexte de l'application
    val context = LocalContext.current

    // Déterminer la destination de départ
    val startDestination = if (authViewModel.getLoginState(context, "loginKey")) {
        "home_screen"
    } else {
        "login_screen"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login_screen") { LoginScreen(navController, viewModel = authViewModel) }
        composable("home_screen") { HomeScreen(navController, viewModel = authViewModel) }
        composable("taskList_screen") { TaskListScreen(navController, viewModel = taskViewModel) }
        composable("newTask_screen") { NewTaskScreen(navController, viewModel = taskViewModel) }
        composable(
            route = "modifyTask_screen/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType})
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            ModifyTaskScreen(navController, viewModel = taskViewModel, taskId = taskId)
        }
        composable(
            route = "taskDetails_screen/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType})
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            TaskDetailsScreen(navController, viewModel = taskViewModel, taskId = taskId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationAppPreview() {
    TaskManagerAppTheme {
        NavigationApp(authViewModel = AuthViewModel(), taskViewModel = TaskViewModel())
    }
}