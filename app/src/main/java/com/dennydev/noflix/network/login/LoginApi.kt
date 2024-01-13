package com.dennydev.noflix.network.login

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.login.Login

interface LoginApi {
    suspend fun login(email: String, password: String): ApiResponse<Login>
}