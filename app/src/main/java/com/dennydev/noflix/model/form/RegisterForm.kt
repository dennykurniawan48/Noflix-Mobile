package com.dennydev.noflix.model.form

import kotlinx.serialization.Serializable

@Serializable
data class RegisterForm(
    val name: String,
    val email: String,
    val password: String
)