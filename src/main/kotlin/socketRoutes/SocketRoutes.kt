package physine.routing.routes

import ch.qos.logback.classic.Logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import physine.services.jwt.JWTService

fun Application.configureSocketRoutes() {
    val jwtService: JWTService by inject()
    val log = LoggerFactory.getLogger("app") as Logger

    routing {

        webSocket("/ws") {
            val token = call.request.queryParameters["jwt"] ?: return@webSocket
            if (!jwtService.isValidToken(token)) {
                call.respond("Invalid or no JWT.")
                call.respond(HttpStatusCode.Unauthorized)
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Unauthorized"))
                return@webSocket
            }

            for (frame in incoming) {
                log.info("Web socket at $frame")
                when (frame) {
                    is Frame.Text -> {
                        val receivedText = frame.readText()
                        send("You said: $receivedText")
                    }
                    is Frame.Binary -> {
                        // Handle binary frames if needed
                    }
                    is Frame.Ping -> {
                        // Handle ping frames if needed
                    }
                    is Frame.Pong -> {
                        // Handle pong frames if needed
                    }
                    is Frame.Close -> {
                        // Handle close frames if needed
                    }
                }
            }
        }
    }
}