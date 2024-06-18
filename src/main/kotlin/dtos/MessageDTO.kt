package physine.dtos

import io.ktor.server.websocket.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import physine.models.MessageModel
import java.util.*

@Serializable
data class SocketRequestDTO(
    val type: String
)

@Serializable
data class MessageDTO(
    @Contextual val groupId: UUID,
    @Contextual val userId: UUID,
    var username: String,
    val content: String
){
    fun toModel(): MessageModel {
        return MessageModel(
            groupId = groupId,
            authorId = userId,
            author = username,
            message = content
        )
    }
}

@Serializable
data class JoinGroupSockDTO(
    @Contextual val groupId: UUID,
    @Contextual val userId: UUID,
    val username: String,
    val defaultWebSocketServerSession: DefaultWebSocketServerSession
)