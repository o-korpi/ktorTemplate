package com.korpi.adapters.primary.routes

import com.korpi.adapters.primary.routes.auth.authRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.routing() {
    routing {
        authRoutes()
        debug()
    }
}
