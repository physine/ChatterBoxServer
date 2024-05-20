package physine.models

import java.util.*

data class UserModel(val username: String,
                     val passwordHash: String,
                     val uuid: UUID)