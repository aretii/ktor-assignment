package com.example.templates

import io.ktor.server.html.*
import kotlinx.html.*

class LoginTemplate : Template<HTML> {
    override fun HTML.apply() {
        head {
            title { +"Login" }
        }
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
