package com.korpi.web

import com.korpi.config.SessionConfig
import io.ktor.http.*
import java.util.*

object CookieFactory {
    /** Creates a "standard" cookie with all default security settings */
    fun standardCookie(name: String, value: String, maxAge: Int = 31536000) = Cookie(
        name,
        value,
        path = "/",
        maxAge = maxAge,
        httpOnly = true,
        //secure = true,
        extensions = mapOf("SameSite" to "lax")
    )

    fun sessionCookie(sessionId: String) = standardCookie("session", sessionId, SessionConfig.sessionTtl.toInt())
    fun sessionCookie(sessionId: UUID) = sessionCookie(sessionId.toString())

    /** Returns a cookie that will be removed */
    fun killCookie(name: String) = standardCookie(name, "", -1)
}