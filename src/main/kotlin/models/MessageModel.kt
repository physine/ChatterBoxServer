package physine.models

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MessageModel(
    @Contextual val groupId: UUID,
    @Contextual val authorId: UUID,
    val author: String,
    val message: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Contextual val timestamp: Date = Date()
)