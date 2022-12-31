package com.korpi.web.security

import com.korpi.config.AuthConfig
import com.korpi.config.SessionConfig
import com.korpi.web.CookieFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*


val CookieAuthPlugin = createRouteScopedPlugin(name = "CookieAuthPlugin") {
    onCall { call ->
        val sessionStorage = SessionConfig.sessionStorage
        val onAuthFailureDestination = AuthConfig.loginPath

        suspend fun challenge() { // Todo: Extract this into some sort of configuration
            call.response.cookies.append("target", call.request.uri, maxAge = 600L) // Todo: extract Ttl
            call.respondRedirect(onAuthFailureDestination)
        }

        val sessionId: String = call.request.cookies["session"] ?:
            return@onCall challenge()

        if (!sessionStorage.exists(UUID.fromString(sessionId)))
            return@onCall challenge()

        // Extend session TTL
        call.response.cookies.append(CookieFactory.sessionCookie(sessionId))
        // Extend TTL in session storage
        SessionConfig.sessionStorage.extend(sessionId)
    }
}


