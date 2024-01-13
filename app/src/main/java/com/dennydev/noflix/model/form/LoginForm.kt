package com.dennydev.noflix.model.form

import kotlinx.serialization.Serializable

@Serializable
data class LoginForm(
    val email: String,
    val password: String
)
