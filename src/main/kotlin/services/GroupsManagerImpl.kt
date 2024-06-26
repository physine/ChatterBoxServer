package physine.services

import kotlinx.serialization.encodeToString
import physine.dtos.CreateGroupDTO
import physine.dtos.DeleteGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.JoinGroupSockDTO
import physine.dtos.LeaveGroupDTO
import physine.dtos.MessageDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse
import physine.models.responces.GroupResponses.alreadyInGroupResponse
import physine.models.responces.GroupResponses.groupCreatedResponse
import physine.models.responces.GroupResponses.groupNameNotAvailableResponse
import physine.models.responces.GroupResponses.groupNameNotValidResponse
import physine.models.responces.GroupResponses.groupNotFoundResponse
import physine.models.responces.GroupResponses.invalidGroupOperationResponse
import physine.models.responces.GroupResponses.noSuchGroupResponse
import physine.models.responces.GroupResponses.operationSuccessfulResponse
import physine.models.responces.GroupResponses.userAddedToGroup
import physine.models.responces.GroupResponses.userNotFoundResponse
import physine.models.responces.GroupResponses.userRemovedFromGroup
import physine.repositories.GroupRepository
import physine.repositories.UserRepository
import physine.routing.json
import physine.utils.CredentialValidation.validateGroupName
import java.util.*

class GroupsManagerImpl(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val connectionManager: ConnectionManager,
) : GroupsManager {

    private val groups: MutableMap<UUID, GroupModel> = mutableMapOf()

    init {
        groupRepository.getAll().forEach { group: GroupModel -> groups[group.groupId] = group }
    }

    override fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse {
        if(!validateGroupName(createGroupDTO.groupName)) return groupNameNotValidResponse()
        if(!groupRepository.isGroupNameAvailable(createGroupDTO.groupName)) return groupNameNotAvailableResponse()
        val group = createGroupDTO.toModel()
        groupRepository.createGroup(group)
        groups[group.groupId] = group
        return groupCreatedResponse()
    }

    override fun deleteGroup(deleteGroupDTO: DeleteGroupDTO): GroupResponse {
        val groupId = deleteGroupDTO.groupId
        val userId = deleteGroupDTO.userId
        val group = groups[groupId] ?: return groupNotFoundResponse()
        if (group.creatorId != userId) return invalidGroupOperationResponse()
        groups.remove(groupId)
        // TODO: delete group from db
        // TODO: delete all messages of group from db
        // TODO: end all active web socket connections for the group
        groupRepository.deleteGroup(groupId)
        return operationSuccessfulResponse()
    }

    override fun addUserToGroup(joinGroupDTO: JoinGroupDTO): GroupResponse {
        val group = groups[joinGroupDTO.groupId] ?: return noSuchGroupResponse()
        if(group.containsMember(joinGroupDTO.userId)) return alreadyInGroupResponse()
        val user = userRepository.getUserById(joinGroupDTO.userId) ?: return userNotFoundResponse()
        group.addMember(user)
        // TODO: create a web socket connection for this client for this chat group
        return userAddedToGroup()
    }

    override fun addUserToGroup(joinGroupSockDTO: JoinGroupSockDTO): GroupResponse {
        val group = groups[joinGroupSockDTO.groupId] ?: return noSuchGroupResponse()
        if(group.containsMember(joinGroupSockDTO.userId)) return alreadyInGroupResponse()
        val user = userRepository.getUserById(joinGroupSockDTO.userId) ?: return userNotFoundResponse()
        group.addMember(user)
        connectionManager.addUserToGroup(joinGroupSockDTO)
        return userAddedToGroup()
    }

    override fun removeUserFromGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse {
        val group = groups[leaveGroupDTO.groupId] ?: return noSuchGroupResponse()
        if(!group.containsMember(leaveGroupDTO.userId)) return userNotFoundResponse()
        group.removeMember(leaveGroupDTO.userId)
        // TODO: remove the web socket connection for this client for this chat group
        return userRemovedFromGroup()
    }

    override fun availableGroups(): String {
        return json.encodeToString( groups.values.map { it.toSummary() } )
    }

    override fun incomingMessage(messageDTO: MessageDTO): GroupResponse {
        // TODO: check if valid message (not empty, etc)

        // TODO: check if group exits
        val group = groups[messageDTO.groupId] ?: return noSuchGroupResponse()

        // TODO: add message to group messages
        group.addMessage(messageDTO.toModel())

        // TODO: add message to messages db

        // TODO: broadcast
        connectionManager.broadcastMessage(messageDTO)
        return operationSuccessfulResponse()
    }
}