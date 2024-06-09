package physine.services

import physine.dtos.CreateGroupDTO
import physine.dtos.JoinGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse
import physine.models.responces.GroupResponses.groupCreatedResponse
import physine.models.responces.GroupResponses.groupNameNotAvailableResponse
import physine.models.responces.GroupResponses.groupNameNotValidResponse
import physine.repositories.GroupRepository
import physine.utils.CredentialValidation.validateGroupName

class GroupServiceImpl(private val groupRepository: GroupRepository) : GroupService {

    // group pool ( addUserToGroup(groupName, userId)  )

    override fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse {
        if (!validateGroupName(createGroupDTO.groupName))
            return groupNameNotValidResponse()
        if(!groupRepository.isGroupNameAvailable(createGroupDTO.groupName))
            return groupNameNotAvailableResponse()
        groupRepository.createGroup(createGroupDTO.toModel())
        return groupCreatedResponse()
    }

    override fun joinGroup(joinGroupDTO: JoinGroupDTO): GroupResponse {

        // check if user is already in group, if not add user to group

        TODO("Not yet implemented")
    }

    override fun leaveGroup(group: GroupModel): GroupResponse {
        TODO("Not yet implemented")
    }
}