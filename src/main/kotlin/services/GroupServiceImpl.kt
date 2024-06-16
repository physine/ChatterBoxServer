package physine.services

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import physine.dtos.CreateGroupDTO
import physine.dtos.DeleteGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.LeaveGroupDTO
import physine.models.responces.GroupResponse

class GroupServiceImpl(
    private val groupsManager: GroupsManager
) : GroupService {

    val log = LoggerFactory.getLogger("app") as Logger

    override fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse {
        return groupsManager.createGroup(createGroupDTO)
    }

    override fun deleteGroup(deleteGroupDTO: DeleteGroupDTO): GroupResponse {
        return groupsManager.deleteGroup(deleteGroupDTO)
    }

    override fun joinGroup(joinGroupDTO: JoinGroupDTO): GroupResponse {
        return groupsManager.addUserToGroup(joinGroupDTO)
    }

    override fun leaveGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse {
        return groupsManager.removeUserFromGroup(leaveGroupDTO)
    }

    override fun groupsListing(): String {
        return groupsManager.availableGroups()
    }
}