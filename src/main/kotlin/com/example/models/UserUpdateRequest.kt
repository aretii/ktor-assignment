package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(val username: String?, val email: String?)
