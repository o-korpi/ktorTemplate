package com.korpi.adapters.primary.routes

import com.korpi.web.security.CookieAuthPlugin
import com.korpi.domain.ports.dto.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.debug() {
    route("/debug") {
        install(CookieAuthPlugin)
        get {
            call.respondText("""
                cookies: ${call.request.cookies.rawCookies}
                logged in as ${call.principal<UserSession>()}
            """.trimIndent())
        }
    }

    route("/profile") {
        install(CookieAuthPlugin)
        get {
            call.response.cookies.append("hello", "world")
            call.respondText("logged in as $")
        }
    }


    get("/index") {
        call.respondText("home")
    }
}