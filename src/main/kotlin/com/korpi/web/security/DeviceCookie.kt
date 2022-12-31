package com.korpi.web.security

import com.korpi.adapters.secondary.RedisCache
import io.ktor.http.*
import io.ktor.server.application.*
import java.util.*



fun ApplicationCall.createDeviceCookie() {
    val uuid = UUID.randomUUID()
    RedisCache.addDevice(uuid)
    response.cookies.append(
        Cookie(
            "device",
            uuid.toString()
        )
    )
}



