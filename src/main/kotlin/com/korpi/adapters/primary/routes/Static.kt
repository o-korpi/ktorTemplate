package com.korpi.adapters.primary.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.static() {
    routing {
        static("/") {
            staticBasePackage = "static"
            resources(".")
        }
    }
}