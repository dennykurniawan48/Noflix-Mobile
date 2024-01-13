package com.dennydev.noflix.network.register

import android.util.Log
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.common.Constant
import com.dennydev.noflix.model.form.RegisterForm
import com.dennydev.noflix.model.response.register.Register
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class RegisterApiImpl(val client: HttpClient): RegisterApi {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): ApiResponse<Register> {
        return try{
            val response: HttpResponse = client.post(Constant.URL_REGISTER){
                contentType(ContentType.Application.Json)
                setBody(RegisterForm(name, email, password))
            }
            if(response.status.isSuccess()){
                ApiResponse.Success(response.body())
            }
            else if(response.status == HttpStatusCode.Conflict){
                ApiResponse.Error("Email registered")
            }else{
                ApiResponse.Error("Something went wrong")
            }
        }catch (e: Exception){
            Log.d("error register", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }
}