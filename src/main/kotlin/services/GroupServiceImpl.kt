package physine.services

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import physine.dtos.CreateGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.LeaveGroupDTO
import physine.models.responces.GroupResponse

class GroupServiceImpl(
    private val groupsManagerService: GroupsManagerService
) : GroupService {

    val log = LoggerFactory.getLogger("app") as Logger

    override fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse {
        return groupsManagerService.createGroup(createGroupDTO)
    }

    override fun joinGroup(joinGroupDTO: JoinGroupDTO): GroupResponse {
        return groupsManagerService.addUserToGroup(joinGroupDTO)
    }

    override fun leaveGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse {
        return groupsManagerService.removeUserFromGroup(leaveGroupDTO)
    }
}