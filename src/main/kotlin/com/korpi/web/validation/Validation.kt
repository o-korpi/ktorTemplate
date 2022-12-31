package com.korpi.web.validation

import com.korpi.domain.models.UserCredentials
import com.korpi.domain.services.validation.email
import com.korpi.domain.services.validation.password
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.validation() {
    install(RequestValidation) {
        validate<UserCredentials> { userCredentials ->
            validate(email(userCredentials.email), password(userCredentials.password))
        }
    }
}

internal fun validate(vararg validators: Pair<Boolean, String>): ValidationResult {
    return validators
        .toList()
        .filter { it.first }
        .map { it.second }
        .reduce { acc, s -> acc + s }
        .let {
            println(it)
            if (it.isNotEmpty()) ValidationResult.Invalid(it)
            else ValidationResult.Valid
        }
}
