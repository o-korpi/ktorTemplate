package com.korpi.adapters.secondary

object RedisCache : RedisPersistence() {
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
}
