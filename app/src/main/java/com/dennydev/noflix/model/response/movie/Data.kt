package com.dennydev.noflix.model.response.movie

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String,
    val description: String,
    val id: Int,
    val ref_category: Int,
    val release_year: String,
    val thumbnail: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val slug: String
)