package com.korpi.adapters.secondary.redis

import com.korpi.config.RedisConfig
import com.korpi.config.SessionConfig
import com.korpi.domain.ports.SessionStorage
import com.korpi.domain.ports.dto.UserId
import redis.clients.jedis.params.SetParams
import java.util.*

class RedisSessionStorage : SessionStorage, RedisPersistence() {
    private val keyPrefix: String = RedisConfig.Schema.session
    private val ttl: Long = SessionConfig.sessionTtl

    private fun key(sessionId: UUID): String = "$keyPrefix:$sessionId"
    private fun key(sessionId: String) = "$keyPrefix:$sessionId"

    override suspend fun write(userId: Long, sessionId: UUID) {
        jedis.set(key(sessionId), userId.toString(), SetParams().ex(ttl))
    }

    override suspend fun read(sessionId: UUID): UserId? {
        val res = jedis.get(key(sessionId))
        return if (res == "nil") null
        else UserId(res.toLong())
    }

    override suspend fun exists(sessionId: UUID): Boolean {
        return jedis.exists(key(sessionId))
    }

    override suspend fun invalidate(sessionId: UUID) {
        jedis.del(key(sessionId))
    }

    /** Extend the TTL of a session */
    override suspend fun extend(sessionId: UUID) {
        jedis.expire(key(sessionId), ttl)
    }

    override suspend fun extend(sessionId: String) {
        jedis.expire(key(sessionId), ttl)
    }
}