package physine.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserDTO(val username: String,
                   @Contextual val uuid: UUID,
                   val password: String)

@Serializable
data class CreateUserDTO(val username: String,
                         val password: String)

@Serializable
data class LoginDTO(val username: String,
                    val password: String)

@Serializable
data class ChangePasswordDTO(val username: String,
                             val newPassword: String)

@Serializable
data class InfoDTO(val username: String)