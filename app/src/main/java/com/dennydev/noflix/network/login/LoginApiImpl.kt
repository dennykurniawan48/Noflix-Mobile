package com.dennydev.noflix.network.login

import android.util.Log
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.common.Constant
import com.dennydev.noflix.model.form.LoginForm
import com.dennydev.noflix.model.response.login.Login
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class LoginApiImpl(val client: HttpClient): LoginApi {
    override suspend fun login(email: String, password: String): ApiResponse<Login> {
        return try{
            val response = client.post(Constant.URL_LOGIN){
                contentType(ContentType.Application.Json)
                setBody(LoginForm(email, password))
            }
            if(response.status.isSuccess()) {
                Log.d("response", response.body())
                ApiResponse.Success(response.body())
            }
            else if (response.status.value == 401) {
                ApiResponse.Error("Wrong username or password.")
            } else if (response.status.value == 404) {
                ApiResponse.Error("Email isn't registered.")
            } else {
                Log.d("response", response.body())
                ApiResponse.Error("Something went wrong.")
            }
        }catch (e: Exception){
            Log.d("Error login", e.toString())
            ApiResponse.Error("Something went wrong.")
        }
    }
}