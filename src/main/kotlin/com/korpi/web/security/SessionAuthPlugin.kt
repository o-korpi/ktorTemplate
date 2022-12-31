package com.korpi.web.security

import com.korpi.config.AuthConfig
import com.korpi.config.SessionConfig
import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.UserService
import com.korpi.web.CookieFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*


val SessionAuthPlugin = createRouteScopedPlugin(name = "SessionAuthPlugin") {
    val config = SessionConfig

    onCall { call ->
        val sessionStorage = config.sessionStorage
        val onAuthFailureDestination = config.loginPath

        suspend fun challenge() { // Todo: Extract this into some sort of configuration
            call.response.cookies.append("target", call.request.uri, maxAge = 600L) // Todo: extract Ttl
            call.respondRedirect(onAuthFailureDestination)
        }

        val sessionId: String = call.request.cookies[config.cookieName] ?:
            return@onCall challenge()

        if (!sessionStorage.exists(UUID.fromString(sessionId)))
            return@onCall challenge()

        // Extend session TTL
        call.response.cookies.append(CookieFactory.sessionCookie(sessionId))
        // Extend TTL in session storage
        SessionConfig.sessionStorage.extend(sessionId)
    }
}

object SessionAuth {
    private val config = SessionConfig
    private val userService = UserService
    private fun getSessionId(call: ApplicationCall): String? = call.request.cookies[config.cookieName]

    suspend fun saveSession(call: ApplicationCall, userCredentials: UserCredentials) {
        val sessionId = UUID.randomUUID()
        val userId = userService.findByEmail(userCredentials.email)!!.id
        config.sessionStorage.write(userId, sessionId)
        call.response.cookies.append(CookieFactory.sessionCookie(sessionId))

        if (AuthConfig.deviceCookiesInstalled) call.createDeviceCookie()
    }

    suspend fun endSession(call: ApplicationCall) {
        config.sessionStorage.invalidate(UUID.fromString(getSessionId(call)))
        call.response.cookies.append(CookieFactory.killCookie(config.cookieName))
    }
}
