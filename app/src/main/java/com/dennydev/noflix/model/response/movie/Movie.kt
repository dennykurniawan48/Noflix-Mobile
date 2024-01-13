package com.dennydev.noflix.model.response.movie

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val `data`: Data
)