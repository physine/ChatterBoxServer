package physine.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object GroupsTable : Table("groups") {
    val groupId: Column<UUID> = uuid("group_id")
    val creatorId: Column<UUID> = uuid("creator_id")
    val groupName: Column<String> = varchar("group_name", 50)
}