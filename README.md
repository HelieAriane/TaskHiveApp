# Gestionnaire de Tâches avec Jetpack Compose

## Description

Le **Gestionnaire de Tâches** est une application Android développée avec **Jetpack Compose**, permettant aux utilisateurs de se connecter, de gérer leurs tâches et de naviguer entre différents écrans.  
Ce projet met en pratique :  
- Programmation Orientée Objet (POO)
- Architecture MVVM
- Navigation entre écrans
- Persistance de données avec SharedPreferences et Gson

---

## Objectifs pédagogiques

- Développer une application Android complète.
- Structurer l'application selon **l'architecture MVVM**.
- Appliquer les principes de **POO**.
- Gérer la **session utilisateur** et la **persistance des tâches**.

---

## Fonctionnalités Principales

### 1. Écran de Login
- Authentification avec des identifiants **hardcodés** :
  - **Email** : `user@example.com`
  - **Mot de passe** : `password123`
- Validation des informations et gestion d'erreurs.
- Sauvegarde de l'état de connexion avec **SharedPreferences**.
- Saut de l'écran de login si l'utilisateur est déjà connecté.

### 2. Écran d'Accueil
- Message de bienvenue.
- Bouton pour accéder à la **liste des tâches**.
- Bouton **Se déconnecter** :
  - Efface l'état de connexion.
  - Redirige vers l'écran de login.

### 3. Liste des Tâches
- Affichage de toutes les tâches dans une **LazyColumn**.
- Chaque tâche propose :
  - Marquer comme "Terminée".
  - Modifier ou supprimer.
- Bouton pour **ajouter une nouvelle tâche**.

### 4. Détail d'une Tâche
- Modification d'une tâche existante :
  - **Titre**
  - **Description**
  - **État** (Terminée ou Non terminée)

### 5. Ajouter une Nouvelle Tâche
- Formulaire d'ajout avec :
  - Titre
  - Description
  - État par défaut : **Non terminée**

### 6. Persistance des Données
- Stockage local des tâches en **JSON** dans **SharedPreferences**.
- Sérialisation/Désérialisation avec **Gson**.
- Les tâches persistent après la fermeture de l'application.

---

## Architecture de l'Application

### MVVM
- **Model**
  - `Task` : Classe représentant une tâche (id, titre, description, état).
- **ViewModel**
  - `AuthViewModel` : Gestion de l'authentification.
  - `TaskViewModel` : Gestion des tâches.
- **View**
  - Composants **Jetpack Compose** pour l'interface utilisateur.

---

## Technologies utilisées

- **Kotlin**
- **Jetpack Compose**
- **Navigation Compose**
- **ViewModel**
- **SharedPreferences**
- **Gson**

---

## Instructions pour l'exécution

1. Lancer l'application sur un émulateur ou un appareil Android.
2. Se connecter avec l'email et mot de passe fournis.
3. Utiliser l'application pour ajouter, modifier, marquer ou supprimer des tâches.

---
