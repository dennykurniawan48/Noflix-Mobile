package com.dennydev.noflix.network.movie

import android.util.Log
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.common.Constant
import com.dennydev.noflix.model.response.movie.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

class MovieApiImpl(val client: HttpClient): MovieApi {
    override suspend fun movie(idMovie: String): ApiResponse<Movie> {
        return try{
            val response: Movie = client.get(Constant.URL_MOVIE.replace("{id}", idMovie)).body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            Log.d("Error movie", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }
}