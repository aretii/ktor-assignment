//package com.example
//
//import io.ktor.server.engine.*
//import io.ktor.server.netty.*
//import com.example.plugins.*
//import io.ktor.server.application.*
//import io.ktor.server.plugins.*
//import io.ktor.serialization.*
//import io.ktor.serialization.kotlinx.json.*
//import io.ktor.server.plugins.contentnegotiation.*
//import org.ktorm.database.Database
//
//
//fun  main() {
//    embeddedServer(Netty, port = 8082, host = "0.0.0.0") {
//        install(ContentNegotiation) {
//            json()
//        }
//        configureRouting()
//    }.start(wait = true)
//
//
//}

package com.example

import io.ktor.server.application.*
import com.example.routing.authenticationRoutes
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*

data class UserSession(val username: String) :Principal


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Sessions) {
        cookie<UserSession>("SESSION") {
            cookie.path = "/"
        }
    }

    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session ->
                // Validate the session here
                session
            }
            challenge { call.respondRedirect("/login") }
        }
    }

    authenticationRoutes()
}

