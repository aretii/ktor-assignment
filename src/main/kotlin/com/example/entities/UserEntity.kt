package com.example.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity : Table<Nothing>("users") {
    val id = int("id").primaryKey()
    val username = varchar("username")
    val password = varchar("password")
    val email = varchar("email")
}

