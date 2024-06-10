package physine.models

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class GroupModel(
    @Contextual val groupId: UUID,
    @Contextual val creatorId: UUID,
    val groupName: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Contextual val timestamp: Date = Date(),
    val messages: MutableList<MessageModel> = mutableListOf(),
    val members: MutableMap<@Contextual UUID, UserModel> = mutableMapOf()
){
    fun containsMember(userId: UUID): Boolean {
        return members[userId] != null
    }

    fun addMember(user: UserModel) {
        members[user.uuid] = user
    }

    fun removeMember(userId: UUID) {
        members.remove(userId)
    }

}