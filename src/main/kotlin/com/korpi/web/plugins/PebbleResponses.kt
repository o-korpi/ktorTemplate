package com.korpi.web.plugins

import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondPebbleRelative(template: String, model: Map<String, Any> = mapOf()) {
    respond(PebbleContent(request.uri.removePrefix("/") + template, model))
}

suspend fun ApplicationCall.respondPebbleNested(template: String, model: Map<String, Any> = mapOf()) {
    respond(PebbleContent("templates/$template", model))
}

suspend fun ApplicationCall.respondPebble(template: String, model: Map<String, Any> = mapOf()) {
    respond(PebbleContent(template, model))
}