package physine.models

import java.util.*

data class UserModel(val username: String,
                     var password: String,
                     val uuid: UUID)