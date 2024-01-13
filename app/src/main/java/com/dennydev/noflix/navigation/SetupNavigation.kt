package com.dennydev.noflix.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dennydev.noflix.screen.HomeScreen
import com.dennydev.noflix.screen.LoginScreen
import com.dennydev.noflix.screen.MovieScreen
import com.dennydev.noflix.screen.RegisterScreen
import com.dennydev.noflix.viewmodel.HomeViewModel

@Composable
fun SetupNavigation(navController: NavHostController, startDestination: String, homeViewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = startDestination, modifier = Modifier) {
        composable(Screen.HomeScreen.route) { entry ->
            HomeScreen(
                navController = navController,
                homeViewModel=homeViewModel
            )
        }

        composable(Screen.LoginScreen.route) { entry ->
            LoginScreen(
                navController = navController
            )
        }

        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }

        composable(Screen.MovieScreen.route, arguments = listOf(
            navArgument(name="id"){
                type = NavType.StringType
            }
        )){
            it.arguments?.getString("id")?.let { id ->
                MovieScreen(navController = navController, idMovie = id)
            }
        }
    }
}