package com.dennydev.noflix.repository

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.movie.Movie
import com.dennydev.noflix.model.response.movies.Movies
import com.dennydev.noflix.network.movie.MovieApiImpl
import com.dennydev.noflix.network.movies.MoviesApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class MoviesRepository @Inject constructor(
    val client: HttpClient
) {
    suspend fun movies(): ApiResponse<Movies> = MoviesApiImpl(client).movies()
    suspend fun movie(id: String): ApiResponse<Movie> = MovieApiImpl(client).movie(id)
}