package physine.chatterBoxApi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import physine.configure.module
import physine.kion.appModule

class ChatterBox {
    fun run() {
        startKoin {
            modules(appModule)
        }

        println("Starting Netty ChatterBox")
        embeddedServer(
            Netty,
            port = 8080,
            host = "0.0.0.0",
            module = Application::module)
            .start(wait = true)
    }
}