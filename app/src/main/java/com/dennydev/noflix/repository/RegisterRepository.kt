package com.dennydev.noflix.repository

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.register.Register
import com.dennydev.noflix.network.register.RegisterApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class RegisterRepository @Inject constructor(
    val client: HttpClient
) {
    suspend fun register(name: String, email: String, password: String): ApiResponse<Register>{
        return RegisterApiImpl(client).register(name, email, password)
    }
}