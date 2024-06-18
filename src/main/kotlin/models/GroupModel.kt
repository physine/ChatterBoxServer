package physine.models

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class GroupModel(
    @Contextual val groupId: UUID,
    @Contextual val creatorId: UUID,
    val groupName: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Contextual
    @Serializable(with = DateSerializer::class)
    val timestamp: Date = Date(),

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

    fun addMessage(message: MessageModel) {
        messages.add(message)
    }

    fun toSummary(): GroupSummary {
        return GroupSummary(
            id = this.groupId,
            name = this.groupName,
            memberCount = this.members.size,
            timestamp = this.timestamp,
        )
    }

    @Serializable
    data class GroupSummary(
        @Contextual val id: UUID,
        val name: String,
        val memberCount: Int,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Contextual
        @Serializable(with = DateSerializer::class)
        val timestamp: Date
    )
}

object DateSerializer : KSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        val dateString = dateFormat.format(value)
        encoder.encodeString(dateString)
    }

    override fun deserialize(decoder: Decoder): Date {
        val dateString = decoder.decodeString()
        return dateFormat.parse(dateString)!!
    }
}