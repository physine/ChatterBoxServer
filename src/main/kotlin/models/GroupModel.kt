package physine.models

import java.util.*

data class GroupModel(
    val groupId: UUID,
    val creatorId: UUID,
    val groupName: String,
    val messages: MutableList<String> = mutableListOf()
)