package com.korpi.adapters.primary.routes.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.korpi.config.AuthConfig
import com.korpi.config.SessionConfig
import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.UserService
import com.korpi.web.plugins.respondPebbleNested
import com.korpi.web.security.createDeviceCookie
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.login() {

    get("/login") {
        println(call.request.uri)
        call.respondPebbleNested("auth/login.html")
    }

    post("/login") {
        val userCredentials = call.getUserCredentials() ?: return@post

        when(login(userCredentials).also { println("Login status: $it") }) {
            LoginStatus.OK -> {
                // Session management
                val uuid: UUID = UUID.randomUUID()
                val userId = UserService.findByEmail(userCredentials.email)!!.id
                SessionConfig.sessionStorage.write(userId, uuid)
                call.response.cookies.append(Cookie("session", uuid.toString(), maxAge = SessionConfig.sessionTtl.toInt()))

                // Give the user a device cookie
                if (AuthConfig.deviceCookiesInstalled) call.createDeviceCookie()

                // Check if the user has a target destination, if so redirect there, else default redirect route
                val defaultPath = "/profile" // Todo: Extract
                val targetPath: String? = call.request.cookies["target"]
                if (targetPath != null) {
                    println("Redirect to $targetPath")
                    call.respondRedirect(targetPath)
                } else {
                    call.respondRedirect(defaultPath)
                }
            }
            LoginStatus.USER_NOT_FOUND, LoginStatus.INCORRECT_PASSWORD -> {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}

private enum class LoginStatus {
    OK, USER_NOT_FOUND, INCORRECT_PASSWORD
}


private fun login(userCredentials: UserCredentials): LoginStatus {
    val user = UserService.findByEmail(userCredentials.email) ?: return LoginStatus.USER_NOT_FOUND
    return if (
        BCrypt.verifyer()
            .verify(
                userCredentials.password.toCharArray(),
                user.password
            ).verified
    ) LoginStatus.OK
    else LoginStatus.INCORRECT_PASSWORD
}

