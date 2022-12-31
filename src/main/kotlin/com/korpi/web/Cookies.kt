package com.korpi.web

import com.korpi.config.SessionConfig
import com.korpi.domain.ports.dto.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.cookies() {
    install(Sessions) {
        cookie<UserSession>("session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = SessionConfig.sessionTtl
            // cookie.secure = true  // TODO: IMPORTANT: Uncomment on deployment
            cookie.extensions["SameSite"] = "lax"
        }

    }
}