package physine.repositories

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import physine.db.GroupsTable
import physine.db.MessagesTable
import physine.db.toGroupModel
import physine.db.toLocalDateTime
import physine.models.GroupModel
import physine.models.MessageModel
import java.util.*

class GroupRepositoryImpl : GroupRepository {
    override fun isGroupNameAvailable(groupName: String): Boolean {
        return transaction {
            val count = GroupsTable.select (GroupsTable.groupName eq groupName).count()
            count.toInt() == 0
        }
    }

    override fun createGroup(group: GroupModel) {
        transaction {
            GroupsTable.insert {
                it[groupId] = group.groupId
                it[creatorId] = group.creatorId
                it[groupName] = group.groupName
                it[timestamp] = group.timestamp.toLocalDateTime()
            }
        }
    }

    override fun deleteGroup(groupId: UUID) {
        transaction {
            GroupsTable.deleteWhere {
                GroupsTable.groupId eq groupId
            }
            // TODO: also delete all messages for the group
        }
    }

    override fun getAll(): List<GroupModel> {
        return transaction {
            GroupsTable.selectAll().map { toGroupModel(it) }
        }
    }

    override fun saveMessage(message: MessageModel) {
        transaction {
            MessagesTable.insert {

            }
        }
    }
}