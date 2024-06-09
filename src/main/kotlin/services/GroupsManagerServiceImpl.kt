package physine.services

import physine.models.GroupModel
import physine.repositories.GroupRepository
import java.util.*

class GroupsManagerServiceImpl(groupRepository: GroupRepository) : GroupsManagerService {

    private val groups: Map<UUID, GroupModel> = mutableMapOf()

    override fun createGroup(group: GroupModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(group: GroupModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun addUserToGroup(groupName: String, userID: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeUserFromGroup(groupName: String, userID: UUID): Boolean {
        TODO("Not yet implemented")
    }
}