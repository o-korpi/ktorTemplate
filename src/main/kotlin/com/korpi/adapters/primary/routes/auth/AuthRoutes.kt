package com.korpi.adapters.primary.routes.auth

import io.ktor.server.routing.*


fun Route.authRoutes() {
    route("") {
        login()
        register()
        logout()
    }
}
