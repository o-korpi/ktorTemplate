package com.korpi.adapters.primary.routes

import com.korpi.domain.ports.dto.UserSession
import com.korpi.web.security.SessionAuthPlugin
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.debug() {
    route("/debug") {
        install(SessionAuthPlugin)
        get {
            call.respondText("""
                cookies: ${call.request.cookies.rawCookies}
                logged in as ${call.principal<UserSession>()}
            """.trimIndent())
        }
    }

    route("/profile") {
        install(SessionAuthPlugin)
        get {
            call.response.cookies.append("hello", "world")
            call.respondText("logged in as $")
        }
    }


    get("/index") {
        call.respond(PebbleContent("templates/index.html", mapOf()))
    }
}