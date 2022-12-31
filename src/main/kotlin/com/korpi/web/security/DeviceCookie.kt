package com.korpi.web.security

import com.korpi.adapters.secondary.RedisCache
import com.korpi.config.RedisConfig
import com.korpi.domain.ports.dto.UserId
import io.ktor.http.*
import io.ktor.server.application.*
import java.util.*



fun ApplicationCall.createDeviceCookie(userId: Long) {
    val uuid = UUID.randomUUID()
    RedisCache.write(
        RedisCache.createKey(RedisConfig.Schema.device, uuid.toString()),
        userId.toString()
    )
    response.cookies.append(
        Cookie(
            "device",
            uuid.toString()
        )
    )
}

fun ApplicationCall.createDeviceCookie(userId: UserId) {
    createDeviceCookie(userId.value)
}



