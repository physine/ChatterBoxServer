package physine.services

import physine.dtos.CreateGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse

interface GroupService {
    fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse
    fun joinGroup(group: GroupModel): GroupResponse
    fun leaveGroup(group: GroupModel): GroupResponse
}