package com.multissue.wit.core.datastore

import com.multissue.wit.core.datastore.storage.WitSerializer

data class User(
    val name: String,
    val age: Int
)

val userSerializer = object : WitSerializer<User> {
    override fun encode(value: User): String = "${value.name},${value.age}"
    override fun decode(string: String): User {
        val parts = string.split(",")
        return User(parts[0], parts[1].toInt())
    }
}