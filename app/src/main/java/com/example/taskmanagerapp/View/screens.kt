package com.example.taskmanagerapp.View

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.ViewModel.AuthViewModel
import com.example.taskmanagerapp.ViewModel.TaskViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Récupère le message d'erreur du ViewModel
    val errorMessage = viewModel.errorMessage

    // Contexte de l'application
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 72.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Titre et logo
            Text(
                text = "TaskHive",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3e7287)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription= "Logo",
                modifier = Modifier.size(150.dp),
                colorFilter = ColorFilter.tint(Color(0xFF3e7287))
            )
        }
        Spacer(Modifier.height(16.dp))

        // Message de bienvenue
        Text(
            text = "Connectez-vous pour commencer.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(80.dp))

        // Champ email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.errorMessage = "" // Réinitialise le message d'erreur
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Affiche le message d'erreur si nécessaire
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(56.dp))

        // Bouton de connexion
        Button(
            onClick = {
                viewModel.authenticate(email, password, context) // Authentification
                if (viewModel.isLoggedIn) {
                    navController.navigate("home_screen") // Redirige vers l'écran d'accueil
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(10.dp, clip = true),
            colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Connexion",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: AuthViewModel) {
    // Contexte de l'application
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 72.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Titre de bienvenue
        Text(
            text = "Bienvenue à\nTaskHive",
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3e7287),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Description de l'application
        Text(
            text = "Gérez vos tâches de manière simple et efficace avec TaskHive.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF4a4a4a),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        // Bouton pour naviguer vers la liste des tâches
        Button(
            onClick = {
                navController.navigate("taskList_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp)
                .shadow(10.dp, clip = true),
            colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Tâches, On s'active !",
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(48.dp))

        // Logo de l'applicatio
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription= "Logo",
            modifier = Modifier.size(50.dp),
            colorFilter = ColorFilter.tint(Color(0xFF3e7287))
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Bouton pour se déconnecter
        Button(
            onClick = {
                viewModel.logout(context, "loginKey") // Déconnexion
                navController.navigate("login_screen") // Redirection vers l'écran de connexion
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp)
                .shadow(10.dp, clip = true),
            colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Déconnexion",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel) {
    // Contexte de l'application
    val context = LocalContext.current

    // Observer les tâches du ViewModel de manière réactive
    val tasks = viewModel.tasks.collectAsState(initial = emptyList()).value

    // Charger les tâches au démarrage de l'application
    LaunchedEffect(Unit) {
        viewModel.fetchTasks(context, "taskKey") // Charger les tâches
    }

    Scaffold(
        topBar = {
            // Barre d'outils avec titre et logo
            TopAppBar(
                title = {
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Liste des tâches",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription= "Logo",
                            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                                .size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                backgroundColor = Color(0xFF3e7287),
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)
            )
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                // Bouton flottant pour ajouter une nouvelle tâche
                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(start = 32.dp),
                    containerColor = Color(0xFF3e7287),
                    contentColor = Color.White
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                }

                // Bouton flottant pour ajouter une nouvelle tâche
                FloatingActionButton(
                    onClick = { navController.navigate("newTask_Screen") },
                    containerColor = Color(0xFF3e7287),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter une tâche")
                }
            }
        }
    ) { innerPadding ->
        // Liste des tâches
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("taskDetails_screen/${task.id}") },
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Détails de la tâche
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.title
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = task.description
                            )
                        }

                        Row {
                            // Bouton terminée/en cours
                            IconButton(
                                onClick = {
                                    viewModel.updateTaskStatus(task.id, context, "taskKey")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Terminée ou en cours",
                                    tint = if (task.isCompleted) Color(0xFF39953E) else Color(0xFFe3b92f)
                                )
                            }

                            // Bouton modifier
                            IconButton(
                                onClick = {
                                    // Naviguer vers l'écran de modification avec l'ID de la tâche
                                    navController.navigate("modifyTask_screen/${task.id}")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Modifier la tâche",
                                    tint = Color(0xFF3e7287)
                                )
                            }

                            // Bouton supprimer
                            IconButton(
                                onClick = {
                                    // Supprimer la tâche via le ViewModel
                                    viewModel.deleteTask(task.id, context, "taskKey")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer la tâche",
                                    tint = Color(0xFFa33939)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }

    // Contexte de l'application
    val context = LocalContext.current

    Scaffold(
        topBar = {
            // Barre d'outils avec titre et logo
            TopAppBar(
                title = {
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Nouvelle tâche",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription= "Logo",
                            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                                .size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                backgroundColor = Color(0xFF3e7287),
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Champ pour le titre
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Titre de la tâche") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Champ pour la description
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = {
                                    isCompleted = it
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tâche terminée")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Bouton pour ajouter la tâche
                Button(
                    onClick = {
                        errorMessage = ""
                        successMessage = ""
                        if (title.isNotBlank() && description.isNotBlank()) {
                            successMessage = "Tâche ajoutée avec succès"
                            viewModel.addTask(title, description, isCompleted, context, "taskIdKey", "taskKey")
                            navController.popBackStack()
                        } else {
                            errorMessage = "Tous les champs doivent être remplis"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(10.dp, clip = true),
                    colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Ajouter",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                // Message de succès
                if (successMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = successMessage,
                        color = Color(0xFF39953E),
                        fontSize = 16.sp
                    )
                }

                // Message d'erreur
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        color = Color(0xFFa33939),
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}

@Composable
fun ModifyTaskScreen(navController: NavController, viewModel: TaskViewModel, taskId: Int?) {
    // Observer les tâches disponibles depuis le ViewModel
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())

    // Trouver la tâche à modifier dans la liste des tâches
    val task = tasks.value.find { it.id == taskId }

    // Si la tâche est nulle, on retourne à l'écran précédent
    if (task == null) {
        return
    }

    // Contexte de l'application
    val context = LocalContext.current

    var newTitle by remember { mutableStateOf(task.title) }
    var newDescription by remember { mutableStateOf(task.description) }
    var isCompleted by remember { mutableStateOf(task.isCompleted) }

    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // Barre d'outils avec titre et logo
            TopAppBar(
                title = {
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Modifier la tâche",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription= "Logo",
                            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                                .size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                backgroundColor = Color(0xFF3e7287),
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)
            )
        },
        content = { innerPadding  ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Champ pour éditer le titre
                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = { Text("Titre de la tâche") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Champ pour éditer la description
                        OutlinedTextField(
                            value = newDescription,
                            onValueChange = { newDescription = it },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Checkbox pour indiquer si la tâche est terminée
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = {
                                    isCompleted = it
                                    viewModel.saveTask(context, "taskKey", viewModel.tasks.value)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tâche terminée")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Bouton pour sauvegarder les modifications de la tâche
                Button(
                    onClick = {
                        if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                            if (taskId != null) {
                                viewModel.modifyTask(taskId, newTitle, newDescription, isCompleted, context, "taskKey")
                                successMessage = "Tâche modifiée avec succès"
                                navController.popBackStack() // Retour à l'écran taskListScreen
                            }
                        } else {
                            errorMessage = "Tous les champs doivent être remplis"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(10.dp, clip = true),
                    colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Sauvegarder",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                // Message de succès
                if (successMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = successMessage,
                        color = Color(0xFF39953E),
                        fontSize = 16.sp
                    )
                }

                // Message d'erreur'
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        color = Color(0xFFa33939),
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}

@Composable
fun TaskDetailsScreen(navController: NavController, viewModel: TaskViewModel, taskId: Int?) {
    // Collecter les tâches de manière réactive
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())

    // Recherche de la tâche
    val task = tasks.value.find { it.id == taskId }

    // Contexte de l'application
    val context = LocalContext.current

    // Si la tâche n'est pas trouvée, retour à l'écran précédent
    if (task == null) {
        return
    }

    Scaffold(
        topBar = {
            // Barre d'outils avec titre et logo
            TopAppBar(
                title = {
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Détails de la tâche - ${task.title}",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription= "Logo",
                            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                                .size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                backgroundColor = Color(0xFF3e7287),
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
            }
        },
        content = { innerPadding  ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Titre
                Text(
                    text = task.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3e7287),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Description
                Text(
                    text = task.description,
                    fontSize = 16.sp,
                    color = Color(0xFF4a4a4a),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Statut de la tâche
                Text(
                    text = if (task.isCompleted) "Tâche terminée" else "Tâche en cours",
                    fontSize = 16.sp,
                    color = if (task.isCompleted) Color(0xFF39953E) else Color(0xFFe3b92f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Bouton pour modifier la tâche
                    Button(
                        onClick = {
                            Log.d("Navigation", "Navigating to modifyTask_screen/${task.id}")
                            navController.navigate("modifyTask_screen/${task.id}")
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF3e7287)),
                        modifier = Modifier.weight(1f).padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Modifier",
                            color = Color.White
                        )
                    }

                    // Bouton pour supprimer la tâche
                    Button(
                        onClick = {
                            viewModel.deleteTask(task.id, context, "taskKey")
                            navController.popBackStack() // Retour à la liste des tâches
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFa33939)),
                        modifier = Modifier.weight(1f).padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Supprimer",
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}