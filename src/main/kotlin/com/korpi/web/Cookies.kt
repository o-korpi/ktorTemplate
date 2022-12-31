package com.korpi.web

import io.ktor.server.application.*

fun Application.cookies() {
    /*install(Sessions) {
        cookie<UserSession>("session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = SessionConfig.sessionTtl
            // cookie.secure = true  // TODO: IMPORTANT: Uncomment on deployment
            cookie.extensions["SameSite"] = "lax"
        }

    }

     */
}