package physine.services

import physine.dtos.CreateGroupDTO
import physine.models.GroupModel
import physine.models.responces.GroupResponse
import physine.repositories.GroupRepository
import physine.utils.CredentialValidation.validateGroupName

class GroupServiceImpl(private val groupRepository: GroupRepository) : GroupService {
    override fun createGroup(createGroupDTO: CreateGroupDTO): GroupResponse {

        // check if group name is valid
        if (!validateGroupName(createGroupDTO.groupName))
            return GroupResponse()
        // check if group name already exists
        if(!groupRepository.isGroupNameAvailable(createGroupDTO.groupName))
            return GroupResponse()

        // create new group
        groupRepository.createGroup(createGroupDTO.toModel())
        return GroupResponse()
    }

    override fun joinGroup(group: GroupModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun leaveGroup(group: GroupModel): Boolean {
        TODO("Not yet implemented")
    }
}