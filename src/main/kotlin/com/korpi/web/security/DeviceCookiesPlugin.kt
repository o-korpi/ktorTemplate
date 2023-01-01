package com.korpi.web.security

import com.korpi.config.AuthConfig
import com.korpi.config.CacheConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/** Lock users without a device cookie out if enabled */
val DeviceCookiesPlugin = createApplicationPlugin(name = "DeviceCookiesPlugin") {
    AuthConfig.deviceCookiesInstalled = true

    onCall {
        if (CacheConfig.deviceCookieStorage.deviceCookiesEnabled()) {
            if (!CacheConfig.deviceCookieStorage.deviceExists(
                it.request.cookies[AuthConfig.deviceCookieName] ?: return@onCall it.respond(HttpStatusCode.Unauthorized)
            )) {
                return@onCall it.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
