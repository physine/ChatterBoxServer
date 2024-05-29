package physine.config

import io.ktor.server.application.*
import physine.db.TableInitializer
import physine.routing.routes.configureUserRoutes

fun Application.module() {
    TableInitializer.init()
    configureLogger()
    configureJwtValidation()
    configureUserRoutes()
    configureSerialization()
}