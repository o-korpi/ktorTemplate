package com.korpi.config

import com.korpi.adapters.secondary.redis.RedisSessionStorage
import com.korpi.domain.ports.SessionStorage

object SessionConfig {
    val sessionStorage: SessionStorage = RedisSessionStorage()
    const val sessionTtl: Long = 600
    const val cookieName: String = "session"
    const val loginPath: String = "/login"

}
