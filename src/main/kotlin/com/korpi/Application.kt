package com.korpi

import com.korpi.adapters.primary.routes.routing
import com.korpi.config.DatabaseConfig
import com.korpi.web.cookies
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.korpi.web.validation.validation
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    DatabaseConfig


    println("Starting")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}


val database: Map<String, String> = mapOf(
    "timothy@example.com" to "hunter25"
)


fun Application.module() {
    install(ContentNegotiation) { json() }

    validation()

    cookies()

    routing()
}

