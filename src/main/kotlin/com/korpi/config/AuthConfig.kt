package com.korpi.config

import com.korpi.adapters.secondary.RedisAuthFailureStorage

object AuthConfig {
    val authFailureStorage = RedisAuthFailureStorage
    const val deviceCookieName = "device"
    var deviceCookiesInstalled: Boolean = false  // do not manually edit
    const val lockoutTime: Long = 600
}
