package com.korpi.adapters.primary.routes.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.UserService
import com.korpi.web.plugins.respondPebbleNested
import com.korpi.web.security.SessionAuth
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.login() {

    get("/login") {
        println(call.request.uri)
        call.respondPebbleNested("auth/login.html")
    }

    post("/login") {
        val userCredentials = call.getUserCredentials() ?: return@post

        when(login(userCredentials).also { println("Login status: $it") }) {
            LoginStatus.OK -> {
                SessionAuth.saveSession(call, userCredentials)

                // Check if the user has a target destination, if so redirect there, else default redirect route
                val defaultPath = "/profile" // Todo: Extract?
                val targetPath: String? = call.request.cookies["target"]
                if (targetPath != null) {
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

