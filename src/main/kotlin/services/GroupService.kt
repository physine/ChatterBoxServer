package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.JoinGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse

interface GroupService {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun joinGroup(joinGroupDTO: JoinGroupDTO): GroupResponse
    fun leaveGroup(group: GroupModel): GroupResponse
}