package com.dennydev.noflix.network.movies

import android.util.Log
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.common.Constant
import com.dennydev.noflix.model.response.movies.Movies
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

class MoviesApiImpl(val client: HttpClient): MoviesApi {
    override suspend fun movies(): ApiResponse<Movies> {
        return try {
            val response: Movies = client.get(Constant.URL_MOVIES).body()

            ApiResponse.Success(response)
        }catch (e: Exception){
            Log.d("Error movies", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }
}