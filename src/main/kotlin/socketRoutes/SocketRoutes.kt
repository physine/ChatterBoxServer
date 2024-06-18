package physine.socketRoutes

import ch.qos.logback.classic.Logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import physine.dtos.JoinGroupSockDTO
import physine.dtos.MessageDTO
import physine.dtos.SocketRequestDTO
import physine.services.GroupService
import physine.services.jwt.JWTService
import java.util.*

fun Application.configureSocketRoutes() {
    val jwtService: JWTService by inject()
    val groupService: GroupService by inject()
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

            val userId = jwtService.getUserIdFromToken(token)
            val groupId = call.request.queryParameters["groupId"]?.let { UUID.fromString(it) } ?: return@webSocket

            for (frame in incoming) {
                log.info("Web socket at $frame")
                when (frame) {
                    is Frame.Text -> {
                        val receivedText = frame.readText()
                        val requestDTO = Json.decodeFromString<SocketRequestDTO>(receivedText)
                        when (requestDTO.type) {
                            "join" -> {
                                val username = jwtService.getUsernameFromToken(token)
                                val joinGroupSockDTO = JoinGroupSockDTO(groupId, userId, username, this)
                                groupService.joinGroup(joinGroupSockDTO)
                            }
                            "message" -> {
                                val messageDTO = Json.decodeFromString<MessageDTO>(receivedText)
                                messageDTO.username = jwtService.getUsernameFromToken(token)
                                groupService.incomingMessage(messageDTO)
                            }
                        }
                    }

                    is Frame.Binary -> {}
                    is Frame.Close -> {}
                    is Frame.Ping -> {}
                    is Frame.Pong -> {}
                }
            }
        }
    }
}