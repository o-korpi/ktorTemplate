package com.korpi.web.security

import com.korpi.config.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.util.*


val CookieAuthPlugin = createRouteScopedPlugin(name = "CookieAuthPlugin") {
    onCall { call ->
        val sessionStorage = SessionConfig.sessionStorage
        val onAuthFailureDestination = AuthConfig.loginPath
        suspend fun challenge() = call.respondRedirect(onAuthFailureDestination)
        val sessionId: String = call.request.cookies["session"] ?:
            return@onCall challenge()

        if (!sessionStorage.exists(UUID.fromString(sessionId)))
            return@onCall
    }
}
