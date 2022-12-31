package com.korpi.adapters.secondary

import com.korpi.config.RedisConfig
import java.util.*

object RedisCache : RedisPersistence() {
    fun deviceCookiesEnabled(): Boolean = jedis.exists(RedisConfig.Schema.deviceCookiesEnabled)

    fun createKey(vararg strings: String) = strings.joinToString("")

    fun <T> write(key: String, value: T) {
        jedis.set(key, value.toString())
    }

    fun read(key: String): String? {
        val res = jedis.get(key)
        return if (res == "nil") null
        else res
    }

    fun exists(key: String): Boolean {
        return jedis.exists(key)
    }

    fun deviceExists(deviceId: String): Boolean {
        return jedis.sismember(RedisConfig.Schema.device, deviceId.toString())
    }

    fun addDevice(deviceId: UUID) {
        jedis.sadd(RedisConfig.Schema.device, deviceId.toString())
    }
}
