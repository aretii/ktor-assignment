package com.example.templates

import io.ktor.server.html.*
import kotlinx.html.*

class RegisterTemplate : Template<HTML> {
    override fun HTML.apply() {
        head {
            title { +"Register" }
        }
        body {
            h1 { +"Register" }
            form(action = "/register", method = FormMethod.post) {
                label { +"Username: " }
                textInput(name = "username") { required = true }
                br
                label { +"Password: " }
                passwordInput(name = "password") { required = true }
                br
                submitInput { value = "Register" }
            }
        }
    }
}
