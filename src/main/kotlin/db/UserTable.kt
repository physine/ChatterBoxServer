package physine.db

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {
    val username = varchar("username", 30).uniqueIndex()
    val password = varchar("password", 32)
}