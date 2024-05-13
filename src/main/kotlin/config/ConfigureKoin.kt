package physine.config

import io.ktor.server.application.*
import org.koin.core.Koin
import physine.kion.appModule

import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.KoinAppDeclaration

//fun Application.configureKoin() {
//    install(Koin) {
//        modules = arrayListOf(appModule)
//    }
//}