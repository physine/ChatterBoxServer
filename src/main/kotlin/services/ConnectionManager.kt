package physine.services

import physine.dtos.JoinGroupSockDTO
import physine.dtos.MessageDTO

interface ConnectionManager {
    fun addUserToGroup(joinGroupSockDTO: JoinGroupSockDTO)
    fun removeUserToGroup()
    fun addGroup()
    fun removeGroup()
    fun broadcastMessage(messageDTO: MessageDTO)
    // get groups
    // get sessions
}