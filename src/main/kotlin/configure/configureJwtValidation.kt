package physine.configure

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import physine.services.jwt.JWTService

fun Application.configureJwtValidation() {
    val jwtService: JWTService by inject()

    val myRealm = jwtService.realm
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm

            verifier(jwtService.generateVerifier())

            validate { credential ->
                val username = credential.payload.getClaim("username").asString()
                if (username != null) {
                    println("Token is valid for user: $username")
                    JWTPrincipal(credential.payload)
                } else {
                    println("Token validation failed: username is null")
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized,
                    "GET OFF MY LAWN! Come Back When You Have A Valid Token")
            }
        }
    }
}