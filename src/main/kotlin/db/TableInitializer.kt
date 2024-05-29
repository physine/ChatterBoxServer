package physine.db

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import physine.config.ConfigureDatabase

object TableInitializer {
    fun init() {
        ConfigureDatabase.init()

        transaction {
            println("[i] Creating db Table UserTable")
            SchemaUtils.create(UserTable)
        }
    }
}