package com.korpi.adapters.primary.routes.auth

import com.korpi.web.security.SessionAuth
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.logout() {
    route("/logout") {
        post {
            SessionAuth.endSession(call)

            call.respondRedirect("login")
        }
    }
}

