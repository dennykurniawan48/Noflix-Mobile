package com.dennydev.noflix.network.movie

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.movie.Movie

interface MovieApi {
    suspend fun movie(idMovie: String): ApiResponse<Movie>
}