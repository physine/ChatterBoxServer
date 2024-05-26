package physine.db

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {
    val username = varchar("name", 255)
    val password = varchar("password", 255)
}