package com.dennydev.noflix.model.response.movies

import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    val movies: List<Movy>
)