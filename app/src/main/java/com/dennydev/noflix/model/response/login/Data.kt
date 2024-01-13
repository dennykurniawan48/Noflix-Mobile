package com.dennydev.noflix.model.response.login

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val token: String,
    val user: User
)