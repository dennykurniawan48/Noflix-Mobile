package com.dennydev.noflix.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.navigation.Screen
import com.dennydev.noflix.viewmodel.HomeViewModel
import com.dennydev.noflix.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val errorEmail by loginViewModel.errorEmail
    val errorPassword by loginViewModel.errorPassword
    val email by loginViewModel.email
    val password by loginViewModel.password
    val formValid by loginViewModel.isFormValid
    val showPassword by loginViewModel.showPassword
    val loginResponse by loginViewModel.loginResponse
    val isSignedIn by loginViewModel.isSignedIn

    LaunchedEffect(key1 = isSignedIn){
        if(isSignedIn){
            navController.navigate(Screen.HomeScreen.route){
                popUpTo(Screen.HomeScreen.route){
                    inclusive=true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Login") })
        }
    ) { it ->
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(it)
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text("Email", style = MaterialTheme.typography.labelSmall)
                Text(errorEmail, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = {
                loginViewModel.setEmail(it)
            }, placeholder = {Text("Enter email")}, leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Enter Email")
            }, modifier=Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text("Password", style = MaterialTheme.typography.labelSmall)
                Text(errorPassword, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = password, onValueChange = {
                loginViewModel.setPassword(it)
            }, placeholder = {Text("Enter password")}, leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Enter Password")
            }, modifier=Modifier.fillMaxWidth(), trailingIcon = {
                IconButton(onClick = {
                    loginViewModel.changeShowPassword()
                }) {
                    Icon(imageVector = if(showPassword)  Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = "")
                }
            }, visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if(loginResponse is ApiResponse.Error){
                Spacer(modifier = Modifier.height(24.dp))
                Text(loginResponse.message.toString(), color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                if(loginResponse !is ApiResponse.Loading) {
                    loginViewModel.login(email, password)
                }
            }, enabled = formValid ,modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Text(if(loginResponse is ApiResponse.Loading) "Loading..." else "Login", style = MaterialTheme.typography.bodyLarge)
            }
            if(loginResponse is ApiResponse.Error){
                Spacer(modifier = Modifier.height(24.dp))
                Text(loginResponse.message.toString(), color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = {
                navController.navigate(Screen.RegisterScreen.route)
            }) {
                Text("Dont have an account? Register")
            }
        }
    }
}