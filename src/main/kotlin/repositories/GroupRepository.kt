package physine.repositories

import physine.models.GroupModel

interface GroupRepository {
    fun isGroupNameAvailable(groupName: String): Boolean
    fun createGroup(groupModel: GroupModel)
    fun getAll(): List<GroupModel>
}