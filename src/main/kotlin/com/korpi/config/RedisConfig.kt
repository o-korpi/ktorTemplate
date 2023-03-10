package com.korpi.config

import com.korpi.domain.ports.dto.Host
import com.korpi.domain.ports.dto.Port

object RedisConfig {
    val host = Host("localhost")
    val port = Port(6379)
    object Schema {
        const val session = "session"
        const val device = "device"
        const val deviceCookiesEnabled = "device_cookies_enabled"
        const val authenticationFailures = "auth_failures"
        const val lockout = "locked_out_devices"
    }
}