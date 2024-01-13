package com.dennydev.noflix.repository

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.login.Login
import com.dennydev.noflix.network.login.LoginApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(
    val client: HttpClient
) {
   suspend fun login(email: String, password: String): ApiResponse<Login>{
       return LoginApiImpl(client).login(email, password)
   }
}