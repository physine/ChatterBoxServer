package physine.models

import java.util.*

data class UserModel(
    val uuid: UUID,
    val username: String,
    var password: String
)