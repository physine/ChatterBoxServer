package physine.dtos

import io.ktor.server.auth.jwt.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateGroupRequest(val groupName: String) {
    fun toDTO(principal: JWTPrincipal): CreateGroupDTO {
        return CreateGroupDTO(
            groupName = groupName,
            groupCreator = UUID.fromString(principal.payload.getClaim("uuid").asString())
        )
    }
}

@Serializable
data class JoinGroupRequest(
    @Contextual val groupId: String,
){
    fun toDTO(principal: JWTPrincipal): JoinGroupDTO {
        return JoinGroupDTO(
            groupId = UUID.fromString(groupId),
            userId = UUID.fromString(principal.payload.getClaim("uuid").asString())
        )
    }
}

@Serializable
data class LeaveGroupRequest(
    @Contextual val groupId: String
){
    fun toDTO(principal: JWTPrincipal): LeaveGroupDTO {
        return LeaveGroupDTO(
            groupId = UUID.fromString(groupId),
            userId = UUID.fromString(principal.payload.getClaim("uuid").asString())
        )
    }
}