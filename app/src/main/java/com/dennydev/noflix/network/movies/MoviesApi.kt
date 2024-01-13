package com.dennydev.noflix.network.movies

import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.movies.Movies

interface MoviesApi {
    suspend fun movies(): ApiResponse<Movies>
}