package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.DeleteGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.JoinGroupSockDTO
import physine.dtos.LeaveGroupDTO
import physine.dtos.MessageDTO
import physine.models.responces.GroupResponse

interface GroupsManager {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun deleteGroup(deleteGroupDTO: DeleteGroupDTO): GroupResponse
    fun addUserToGroup(joinGroupDTO: JoinGroupDTO): GroupResponse
    fun addUserToGroup(joinGroupSockDTO: JoinGroupSockDTO): GroupResponse
    fun removeUserFromGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse
    fun availableGroups(): String
    fun incomingMessage(messageDTO: MessageDTO): GroupResponse
}