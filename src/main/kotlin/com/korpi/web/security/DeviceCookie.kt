package com.korpi.web.security

import com.korpi.adapters.secondary.DeviceCookieStorage
import com.korpi.config.AuthConfig
import io.ktor.http.*
import io.ktor.server.application.*
import java.util.*



fun ApplicationCall.createDeviceCookie() {
    val uuid = UUID.randomUUID()
    DeviceCookieStorage.addDevice(uuid)
    response.cookies.append(
        Cookie(
            AuthConfig.deviceCookieName,
            uuid.toString()
        )
    )
}



