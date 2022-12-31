package com.korpi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    @SerialName("name")
    val email: String,
    @SerialName("pass")
    val password: String
)