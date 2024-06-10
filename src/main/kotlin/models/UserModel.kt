package physine.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserModel(
    @Contextual val uuid: UUID,
    val username: String,
    var password: String
)