package com.korpi

import com.korpi.adapters.primary.routes.routing
import com.korpi.config.DatabaseConfig
import com.korpi.web.cookies
import com.korpi.web.security.DeviceCookiesPlugin
import com.korpi.web.validation.validation
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

fun main() {
    println("Starting")
    DatabaseConfig
    println("Database initialized")


    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}


val database: Map<String, String> = mapOf(
    "timothy@example.com" to "hunter25"
)


fun Application.module() {
    install(ContentNegotiation) { json() }
    install(DeviceCookiesPlugin)

    validation()

    cookies()

    val rateLimit = true

    if (rateLimit) {
        install(RateLimit) {
            register(RateLimitName("protected")) {
                rateLimiter(limit = 100, refillPeriod = 60.seconds)
            }
        }
    }


    routing()
}

