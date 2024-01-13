package com.dennydev.noflix.model.response.movies

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val id: Int,
    val release_year: String,
    val thumbnail: String,
    val title: String,
    val slug: String
)