package com.dennydev.noflix.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val unselectedIcon: ImageVector, val selectedIcon: ImageVector, val route: String, val title: String) {
    object HomeScreen : Screen(Icons.Default.Home, Icons.Default.Home, "Home", "Home")
    object LoginScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Login", "Login")
    object RegisterScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Register", "Register")
    object MovieScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Movie/{id}", "Movie")
}