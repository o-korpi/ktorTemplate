package com.korpi.web.security

import com.korpi.config.AuthConfig
import com.korpi.config.CacheConfig
import com.korpi.config.RedisConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/** Lock users without a device cookie out if enabled */
val DeviceCookiesPlugin = createApplicationPlugin(name = "DeviceCookiesPlugin") {
    AuthConfig.deviceCookiesInstalled = true

    onCall {
        if (CacheConfig.cache.read(RedisConfig.Schema.deviceCookiesEnabled) == "true") {
            if (!CacheConfig.cache.exists(
                    CacheConfig.cache.createKey(
                        RedisConfig.Schema.device,
                        it.request.cookies[RedisConfig.Schema.device]
                            ?: return@onCall it.respond(HttpStatusCode.Unauthorized)
                    )
                )
            ) {
                return@onCall it.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}