package physine.repositories

import physine.models.GroupModel
import java.util.*

interface GroupRepository {
    fun isGroupNameAvailable(groupName: String): Boolean
    fun createGroup(groupModel: GroupModel)
    fun deleteGroup(groupId: UUID)
    fun getAll(): List<GroupModel>
}