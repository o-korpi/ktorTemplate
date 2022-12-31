package com.korpi.config

import com.korpi.adapters.secondary.RedisSessionStorage
import com.korpi.domain.ports.SessionStorage

object SessionConfig {
    val sessionStorage: SessionStorage = RedisSessionStorage()
    const val sessionTtl: Long = 600
}

