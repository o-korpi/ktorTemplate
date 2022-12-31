package com.korpi.adapters.secondary

import com.korpi.config.AuthConfig
import com.korpi.config.RedisConfig
import redis.clients.jedis.params.SetParams
import java.text.SimpleDateFormat
import java.util.*

object RedisAuthFailureStorage : RedisPersistence() {
    /** Log auth failure */
    fun addAuthFailure(userEmail: String, time: Date, deviceCookie: String?) {
        jedis.sadd(
            RedisConfig.Schema.authenticationFailures,
            "user=User($userEmail), " +
                    "time=${SimpleDateFormat("yyyy/MM/dd h:mm:ss a".format(time))}, " +
                    "device=$deviceCookie"
        )
    }

    /** Lockout device */
    fun addDeviceLockout(userId: Long, deviceCookie: String) {
        jedis.set(
            "${RedisConfig.Schema.lockout}:$userId",
            deviceCookie,
            SetParams().ex(AuthConfig.lockoutTime)
        )
    }
}