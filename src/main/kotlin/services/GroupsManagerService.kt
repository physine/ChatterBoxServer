package physine.services

import physine.models.GroupModel
import java.util.*

interface GroupsManagerService {
    fun createGroup(group: GroupModel): Boolean
    fun deleteGroup(group: GroupModel): Boolean

    fun addUserToGroup(groupName: String, userID: UUID): Boolean
    fun removeUserFromGroup(groupName: String, userID: UUID): Boolean
}