package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.LeaveGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse

interface GroupsManagerService {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun deleteGroup(group: GroupModel): GroupResponse

    fun addUserToGroup(joinGroupDTO: JoinGroupDTO): GroupResponse
    fun removeUserFromGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse
}