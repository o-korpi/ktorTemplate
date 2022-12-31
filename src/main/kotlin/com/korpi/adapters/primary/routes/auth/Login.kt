package com.korpi.adapters.primary.routes.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.korpi.config.SessionConfig
import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.UserService
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.HttpStatusCode
import java.util.*

fun Route.login() {

    get("/login") {
        call.respondText("Login page")
    }

    post("/login") {
        val userCredentials = call.getUserCredentials() ?: return@post

        when(login(userCredentials).also { println("Login status: ${it::class.simpleName}") }) {
            LoginStatus.OK -> {
                val uuid: UUID = UUID.randomUUID()
                val userId = UserService.findByEmail(userCredentials.email)!!.id
                SessionConfig.sessionStorage.write(userId, uuid)
                call.response.cookies.append(Cookie("session", uuid.toString()))
                println("Logged in as user $userId")
                call.respondRedirect("/profile")
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

