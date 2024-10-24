package com.example.plugins

import com.example.routing.authenticationRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello Ktor!")
        }
        authenticationRoutes() // Ensure this is within the routing block
    }
}
