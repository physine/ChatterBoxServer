package physine.db

import org.jetbrains.exposed.sql.ResultRow
import physine.models.GroupModel
import physine.models.UserModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun ResultRow.toUserModel() = UserModel(
    uuid = this[UsersTable.userId],
    username = this[UsersTable.username],
    password = this[UsersTable.password],
)

fun toGroupModel(row: ResultRow): GroupModel {
    return GroupModel(
        groupId = row[GroupsTable.groupId],
        creatorId = row[GroupsTable.creatorId],
        groupName = row[GroupsTable.groupName],
        timestamp = Date.from(row[GroupsTable.timestamp].atZone(ZoneId.systemDefault()).toInstant())
    )
}

fun Date.toLocalDateTime(): LocalDateTime {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}