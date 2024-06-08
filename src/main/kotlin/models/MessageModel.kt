package physine.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class MessageModel(
    val groupId: UUID,
    val authorId: UUID,
    val author: String,
    val message: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: Date = Date()
)