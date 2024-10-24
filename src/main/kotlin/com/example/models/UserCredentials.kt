package com.example.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserCredentials(val username: String, val password: String, val email: String? = null) {
    fun isValidCredentials() = username.length >= 3 && password.length >= 8

    fun hashedPassword(): String = BCrypt.hashpw(password, BCrypt.gensalt())
}
