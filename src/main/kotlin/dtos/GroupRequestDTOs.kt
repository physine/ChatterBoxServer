package physine.dtos

import io.ktor.server.auth.jwt.*
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