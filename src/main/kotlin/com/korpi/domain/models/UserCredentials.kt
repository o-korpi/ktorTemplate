package com.korpi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)