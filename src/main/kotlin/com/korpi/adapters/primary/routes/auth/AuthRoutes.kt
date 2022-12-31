package com.korpi.adapters.primary.routes.auth

import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*


fun Route.authRoutes() {
    route("") {
        rateLimit(RateLimitName("protected")) {
            login()
            register()
            logout()
        }
    }
}
