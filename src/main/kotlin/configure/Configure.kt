package physine.configure

import io.ktor.server.application.*
import physine.routing.routes.configureUserRoutes

fun Application.module() {
    configureDatabase()
    configureLogger()
    configureJwtValidation()
    configureUserRoutes()
    configureSerialization()
}