package com.korpi.config

import com.korpi.adapters.secondary.redis.RedisAuthFailureStorage

object AuthConfig {
    val authFailureStorage = RedisAuthFailureStorage
    var deviceCookiesInstalled: Boolean = false  // do not manually edit
    const val deviceCookieName = "device"
    const val lockoutTime: Long = 600
}
