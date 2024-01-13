package com.dennydev.noflix.model.response.movies

import kotlinx.serialization.Serializable

@Serializable
data class Movy(
    val `data`: List<Data>,
    val genre: String
)