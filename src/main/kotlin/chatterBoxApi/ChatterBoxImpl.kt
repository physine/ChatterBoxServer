package physine.chatterBoxApi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import physine.config.module
import physine.kion.appModule

class ChatterBoxImpl : ChatterBox {
    override fun run() {
        startKoin {
            modules(appModule)
        }

        println("Development mode: " + System.getProperty("io.ktor.development"))
        System.setProperty("io.ktor.development", "false")
        println("Development mode: " + System.getProperty("io.ktor.development"))
        println("Starting Netty ChatterBox")
        embeddedServer(
            Netty, port = 8080,
            module = Application::module)
            .start(wait = true)
    }
}