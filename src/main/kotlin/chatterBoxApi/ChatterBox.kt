package physine.chatterBoxApi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import physine.config.module
import physine.kion.appModule

class ChatterBox : IChatterBox {
    override fun run() {
        startKoin {
            modules(appModule)
        }

        println("Starting Netty ChatterBox")
        embeddedServer(
            Netty, port = 8080,
            module = Application::module)
            .start(wait = true)
    }
}