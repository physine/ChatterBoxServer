package physine.config

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import physine.api.configureUserRoutes

fun configureKtor() {
    println("Starting up Netty")
    embeddedServer(Netty, port = 8080) {
        println("Configuring Routes")
        configureUserRoutes()
        println("Configuring Serialization")
        configureSerialization()
        println("-------- Configuring Complete --------")
    }.start(wait = true)
}