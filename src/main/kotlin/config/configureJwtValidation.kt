package physine.config

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureJwtValidation() {
    install(Authentication) {
        jwt("auth-jwt") {  // You can name your authentication configuration
            realm = "Ktor Server"
//            verifier(/* JWT verifier setup, e.g., using Jwts library */)
            // TODO:
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}