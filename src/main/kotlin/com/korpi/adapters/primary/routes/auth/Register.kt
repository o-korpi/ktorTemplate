package com.korpi.adapters.primary.routes.auth

import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.UserService
import com.korpi.web.plugins.respondPebbleNested
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.register() {
    route("/register") {
        get {
            call.respondPebbleNested("auth/register.html")
        }

        post {
            val userCredentials = call.getUserCredentials() ?: return@post

            when (register(userCredentials)) {
                RegisterStatus.USER_ALREADY_EXISTS -> call.respond(HttpStatusCode.Conflict)
                RegisterStatus.ERROR -> call.respond(HttpStatusCode.InternalServerError)
                RegisterStatus.SUCCESS -> call.respondRedirect("/login")  // todo: automate
            }
        }
    }
}

private enum class RegisterStatus {
    SUCCESS,
    USER_ALREADY_EXISTS,
    ERROR  // todo: expand on this, what errors may be thrown?
}

private fun register(user: UserCredentials): RegisterStatus {
    if (UserService.findByEmail(user.email) != null) return RegisterStatus.USER_ALREADY_EXISTS
    UserService.create(user.email, user.password) ?: return RegisterStatus.ERROR
    return RegisterStatus.SUCCESS
}
