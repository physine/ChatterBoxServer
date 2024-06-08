package physine.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table("users") {
    val userId: Column<UUID> = uuid("user_id").uniqueIndex()
    val username: Column<String> = varchar("username", 30).uniqueIndex()
    val password: Column<String> = varchar("password", 32)
}