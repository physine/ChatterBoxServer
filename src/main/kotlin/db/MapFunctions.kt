package physine.db

import org.jetbrains.exposed.sql.ResultRow
import physine.models.UserModel

fun ResultRow.toUserModel() = UserModel(
    uuid = this[UsersTable.userId],
    username = this[UsersTable.username],
    password = this[UsersTable.password],
)