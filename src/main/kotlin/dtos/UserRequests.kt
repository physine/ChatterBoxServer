package physine.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateUserRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class ChangePasswordRequest(
    val newPassword: String
)

@Serializable
data class DeleteUserRequest(
    @Contextual val uuid: UUID
)