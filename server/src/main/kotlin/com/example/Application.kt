package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.routes.*
import com.example.database.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        Database().CreateTable("coffee")
    }.start(wait = true)

    
}
