package com.korpi.adapters.secondary.redis

import com.korpi.config.RedisConfig
import redis.clients.jedis.JedisPooled

abstract class RedisPersistence {
    protected val jedis = JedisPooled(RedisConfig.host.value, RedisConfig.port.value)
}
