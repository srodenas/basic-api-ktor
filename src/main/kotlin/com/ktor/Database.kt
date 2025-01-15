package com.ktor

import io.ktor.server.application.*

fun Application.configureDatabases(){
    val driver = "org.postgresql.Driver"
    val url = environment.config.property("database.url").getString()
    val username = environment.config.property("database.username").getString()
    val password = environment.config.property("database.password").getString()

}