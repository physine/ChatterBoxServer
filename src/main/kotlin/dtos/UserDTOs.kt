package physine.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import physine.models.UserModel
import java.util.*

@Serializable
data class CreateUserDTO(val username: String, val password: String) {
    fun toModel(): UserModel {
        return UserModel(UUID.randomUUID(), username, password)
    }
}

@Serializable
data class LoginDTO(
    val username: String,
    val password: String
)

@Serializable
data class ChangePasswordDTO(
    @Contextual val uuid: UUID,
    val newPassword: String
)

@Serializable
data class InfoDTO(val username: String)