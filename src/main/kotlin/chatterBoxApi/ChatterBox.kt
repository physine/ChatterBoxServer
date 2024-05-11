package physine.chatterBoxApi

import org.koin.core.context.startKoin
import physine.config.configureKtor
import physine.kion.appModule

class ChatterBox : IChatterBox {
    override fun run() {
        println("Starting chatterBox")
        startKoin {
            modules(appModule)
        }
        println("Configuring Ktor")
        configureKtor()
    }
}