package com.dennydev.noflix.network.register

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.register.Register

interface RegisterApi {
    suspend fun register(name: String, email: String, password: String): ApiResponse<Register>
}