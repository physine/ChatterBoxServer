package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.DeleteGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.LeaveGroupDTO
import physine.models.responces.GroupResponse

interface GroupsManagerService {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun deleteGroup(deleteGroupDTO: DeleteGroupDTO): GroupResponse
    fun addUserToGroup(joinGroupDTO: JoinGroupDTO): GroupResponse
    fun removeUserFromGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse
    fun availableGroups(): String
}