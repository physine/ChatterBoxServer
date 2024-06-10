package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.JoinGroupDTO
import physine.dtos.LeaveGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse
import physine.models.responces.GroupResponses.alreadyInGroupResponse
import physine.models.responces.GroupResponses.groupCreatedResponse
import physine.models.responces.GroupResponses.groupNameNotAvailableResponse
import physine.models.responces.GroupResponses.groupNameNotValidResponse
import physine.models.responces.GroupResponses.noSuchGroupResponse
import physine.models.responces.GroupResponses.userAddedToGroup
import physine.models.responces.GroupResponses.userNotFoundResponse
import physine.models.responces.GroupResponses.userRemovedFromGroup
import physine.repositories.GroupRepository
import physine.repositories.UserRepository
import physine.utils.CredentialValidation.validateGroupName
import java.util.*

class GroupsManagerServiceImpl(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) : GroupsManagerService {

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

    override fun deleteGroup(group: GroupModel): GroupResponse {
        TODO("Not yet implemented")
    }

    override fun addUserToGroup(joinGroupDTO: JoinGroupDTO): GroupResponse {
        val group = groups[joinGroupDTO.groupId] ?: return noSuchGroupResponse()
        if(group.containsMember(joinGroupDTO.userId)) return alreadyInGroupResponse()
        val user = userRepository.getUserById(joinGroupDTO.userId) ?: return userNotFoundResponse()
        group.addMember(user)
        // TODO: create a web socket connection for this client for this chat group
        return userAddedToGroup()
    }

    override fun removeUserFromGroup(leaveGroupDTO: LeaveGroupDTO): GroupResponse {
        val group = groups[leaveGroupDTO.groupId] ?: return noSuchGroupResponse()
        if(!group.containsMember(leaveGroupDTO.userId)) return userNotFoundResponse()
        group.removeMember(leaveGroupDTO.userId)
        // TODO: remove the web socket connection for this client for this chat group
        return userRemovedFromGroup()
    }
}