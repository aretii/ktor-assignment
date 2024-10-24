package com.example.routing

import com.example.UserSession
import com.example.db.DatabaseConnection
import com.example.templates.RegisterTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.auth.*
import org.mindrot.jbcrypt.BCrypt
import org.ktorm.dsl.*
import com.example.entities.UserEntity
import kotlinx.html.*

fun Application.authenticationRoutes() {
    val db = DatabaseConnection.database  // Use the database connection from the separate file

    routing {
        get("/") {
            call.respondText("Hello Ktor!")
        }

        get("/register") {
            call.respondHtmlTemplate(RegisterTemplate()) {}
        }

        post("/register") {
            val params = call.receiveParameters()
            val username = params["username"]?.lowercase() ?: ""
            val password = params["password"] ?: ""

            if (username.isEmpty() || password.isEmpty()) {
                call.respondText("Username or password cannot be empty.")
                return@post
            }

            val userExists = db.from(UserEntity)
                .select()
                .where { UserEntity.username eq username }
                .map { it[UserEntity.username] }
                .isNotEmpty()

            if (userExists) {
                call.respondText("User already exists")
                return@post
            }

            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

            db.insert(UserEntity) {
                set(it.username, username)
                set(it.password, hashedPassword)
            }

            call.respondRedirect("/login")
        }

        get("/login") {
            call.respondHtml {
                head { title { +"Login" } }
                body {
                    h1 { +"User Login" }
                    form(action = "/login", method = FormMethod.post) {
                        label { +"Username: " }
                        textInput(name = "username") { required = true }
                        br
                        label { +"Password: " }
                        passwordInput(name = "password") { required = true }
                        br
                        submitInput { value = "Login" }
                    }
                }
            }
        }

        post("/login") {
            val params = call.receiveParameters()
            val username = params["username"] ?: ""
            val password = params["password"] ?: ""

            // Fetch user by username from the database
            val user = db.from(UserEntity)
                .select()
                .where { UserEntity.username eq username }
                .map { row ->
                    val dbPassword = row[UserEntity.password]
                    dbPassword
                }.firstOrNull()

            if (user == null || !BCrypt.checkpw(password, user)) {
                call.respondText("Invalid credentials")
                return@post
            }

            // Start a session for the authenticated user
            call.sessions.set(UserSession(username))
            call.respondRedirect("/dashboard")
        }

        post("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
    }
}
