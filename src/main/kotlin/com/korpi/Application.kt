package com.korpi

import com.korpi.adapters.primary.routes.routing
import com.korpi.adapters.primary.routes.static
import com.korpi.config.DatabaseConfig
import com.korpi.web.cookies
import com.korpi.web.security.DeviceCookiesPlugin
import com.korpi.web.validation.validation
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.pebble.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.Database
import kotlin.time.Duration.Companion.seconds

fun main() {
    println("Starting")
    Database.connect(DatabaseConfig.db.connect())
    println("Database initialized")
    println(System.getProperty("java.class.path").split(":").map { it + "\n" })


    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}


fun Application.module() {
    // Must be installed before kotlinx serialization
    install(Pebble) {
        loader(ClasspathLoader())
    }
    install(ContentNegotiation) { json() }
    install(DeviceCookiesPlugin)

    validation()

    cookies()

    val rateLimit = true


    if (rateLimit) {
        install(RateLimit) {
            register(RateLimitName("protected")) {
                requestKey {

                }
                requestWeight { call, _ ->
                    if (call.request.uri.contains("login")) {
                        10
                    } else 1
                }
                rateLimiter(limit = 500, refillPeriod = 60.seconds)
            }
        }
    }


    routing()
    static()
}

