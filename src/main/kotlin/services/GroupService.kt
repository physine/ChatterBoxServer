package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.DeleteGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.JoinGroupSockDTO
import physine.dtos.LeaveGroupDTO
import physine.dtos.MessageDTO
import physine.models.responces.GroupResponse

interface GroupService {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun joinGroup(joinGroupDTO: JoinGroupDTO): GroupResponse
    fun joinGroup(joinGroupSockDTO: JoinGroupSockDTO)
    fun leaveGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse
    fun deleteGroup(deleteGroupDTO: DeleteGroupDTO): GroupResponse
    fun groupsListing(): String
    fun incomingMessage(messageDTO: MessageDTO)
}