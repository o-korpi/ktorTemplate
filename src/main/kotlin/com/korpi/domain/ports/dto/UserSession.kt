package com.korpi.domain.ports.dto

import io.ktor.server.auth.*
import java.util.*

data class UserSession(
    val id: UUID
): Principal