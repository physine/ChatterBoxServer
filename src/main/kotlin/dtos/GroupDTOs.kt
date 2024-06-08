package physine.dtos

import physine.models.GroupModel
import java.util.*

data class CreateGroupDTO(
    val groupName: String,
    val groupCreator: UUID
){
    fun toModel(): GroupModel {
        return GroupModel(
            groupId = UUID.randomUUID(),
            creatorId = groupCreator,
            groupName = groupName,
            messages = mutableListOf()
        )
    }
}