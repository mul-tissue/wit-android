package com.multissue.wit.core.datastore.storage

interface WitSerializer<T> {
    fun encode(value: T): String
    fun decode(value: String): T
}