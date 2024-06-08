package physine.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object MessagesTable : Table("messages") {
    val groupId: Column<UUID> = uuid("message_id").uniqueIndex()
    val authorId: Column<UUID> = uuid("author_user_id")
    val author: Column<String> = varchar("author_username", 30)
    val timestamp: Column<LocalDateTime> = datetime("timestamp")
    val message: Column<String> = varchar("message", 500)
}