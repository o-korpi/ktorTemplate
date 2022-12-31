package com.korpi.web.security

import com.korpi.config.SessionConfig
import com.korpi.domain.ports.dto.UserId
import com.korpi.domain.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.util.*

val RoleAuthPlugin = createRouteScopedPlugin(name = "RoleAuthPlugin") {
    onCall { call ->
        val sessionStorage = SessionConfig.sessionStorage
        suspend fun challenge(msg: String = "") =
            call.respond(HttpStatusCode.Unauthorized, msg)

        val sessionId: String = call.request.cookies["session"] ?: return@onCall challenge("No existing session")

        val userId: UserId =
            sessionStorage.read(UUID.fromString(sessionId)) ?: return@onCall challenge("No existing session")

        val user = UserService.findById(userId.value) ?: return@onCall challenge("No such privileged user")

        if (user.email != "admin@example.com")
            return@onCall challenge("Unauthorized")
    }
}