package physine.configure

import io.ktor.server.application.*
import physine.httpRouting.routes.configureGroupRoutes
import physine.routing.routes.configureSocketRoutes
import physine.routing.routes.configureUserRoutes

fun Application.module() {
    configureDatabase()
    configureLogger()

    configureSerialization()
    configureJwtValidation()

    configureUserRoutes()
    configureGroupRoutes()

    configureSockets()
    configureSocketRoutes()

}