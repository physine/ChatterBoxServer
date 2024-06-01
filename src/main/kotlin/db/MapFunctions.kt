package physine.db

import org.jetbrains.exposed.sql.ResultRow
import physine.models.UserModel

fun ResultRow.toUserModel() = UserModel(
    uuid = this[UserTable.id].value,
    username = this[UserTable.username],
    password = this[UserTable.password],
)