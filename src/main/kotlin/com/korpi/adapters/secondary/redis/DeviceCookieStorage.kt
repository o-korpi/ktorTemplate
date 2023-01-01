package com.korpi.adapters.secondary.redis

import com.korpi.config.RedisConfig
import java.util.*

object DeviceCookieStorage : RedisPersistence() {
    fun deviceCookiesEnabled(): Boolean = jedis.exists(RedisConfig.Schema.deviceCookiesEnabled)

    fun deviceExists(deviceId: String): Boolean {
        return jedis.sismember(RedisConfig.Schema.device, deviceId)
    }

    fun addDevice(deviceId: UUID) {
        jedis.sadd(RedisConfig.Schema.device, deviceId.toString())
    }
}
