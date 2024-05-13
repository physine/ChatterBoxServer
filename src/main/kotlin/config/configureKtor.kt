package physine.config

import io.ktor.server.application.*
import physine.routing.routes.configureUserRoutes

fun Application.module() {
    configureUserRoutes()
    configureSerialization()
//    configureJwtValidation()
}