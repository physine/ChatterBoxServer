package physine.dtos

import io.ktor.server.auth.jwt.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateUserRequest(val username: String, val password: String) {
    fun toDTO(): CreateUserDTO {
        return CreateUserDTO(
            username = this.username,
            password = this.password,
        )
    }
}

@Serializable
data class LoginRequest(val username: String, val password: String) {
    fun toDTO(): LoginDTO {
        return LoginDTO(
            username = this.username,
            password = this.password
        )
    }
}

@Serializable
data class ChangePasswordRequest(
    val newPassword: String
) {
    fun toDTO(principal: JWTPrincipal): ChangePasswordDTO {
        return ChangePasswordDTO(
            uuid = UUID.fromString(principal.payload.getClaim("uuid").asString()),
            newPassword = this.newPassword
        )
    }
}

@Serializable
data class DeleteUserRequest(
    @Contextual val uuid: UUID
)