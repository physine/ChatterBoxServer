package physine.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(val username: String,
                   val password: String)