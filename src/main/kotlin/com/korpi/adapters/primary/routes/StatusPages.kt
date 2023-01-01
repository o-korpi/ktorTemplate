package com.korpi.adapters.primary.routes

import com.korpi.web.plugins.respondPebbleNested
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.statusPageRouting() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondPebbleNested("error/error404.html")
        }
    }
}