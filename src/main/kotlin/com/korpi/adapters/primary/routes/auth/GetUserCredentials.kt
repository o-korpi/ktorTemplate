package com.korpi.adapters.primary.routes.auth

import com.korpi.domain.models.UserCredentials
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.getUserCredentials(): UserCredentials? = try {
    receive()
} catch (e: RequestValidationException) {
    respond(HttpStatusCode.BadRequest, "e: ${e.value}")
    null
}