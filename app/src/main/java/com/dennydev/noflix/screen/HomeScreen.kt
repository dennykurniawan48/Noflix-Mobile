package com.dennydev.noflix.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dennydev.noflix.components.ListMovie
import com.dennydev.noflix.navigation.Screen
import com.dennydev.noflix.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val movies by homeViewModel.movies

    val loadMovie by homeViewModel.loadMovie

    LaunchedEffect(key1 = loadMovie){
        if(loadMovie){
            homeViewModel.getMovies()
            homeViewModel.changeLoadMovie()
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "No-Flix")
        }, actions = {
            IconButton(onClick = {
                homeViewModel.logout()
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.LoginScreen.route){
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "")
            }
        })
    }) { values ->
        LazyColumn(modifier= Modifier
            .fillMaxSize()
            .padding(values)){
            movies.data?.movies?.let {
                items(it){
                    ListMovie(navController, data = it)
                }
            }
        }
    }
}