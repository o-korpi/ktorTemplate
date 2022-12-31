package com.korpi.adapters.primary.routes.auth

import com.korpi.config.SessionConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.logout() {
    route("/logout") {
        post {
            val sessionId = call.request.cookies["session"]
            val uuid = UUID.fromString(sessionId)
            SessionConfig.sessionStorage.invalidate(uuid)
            call.response.cookies.append(
                "session",
                "",
                maxAge = -1L
            )
            call.respondRedirect("login")
        }
    }
}

