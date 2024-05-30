package physine.configure

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import physine.db.UserTable

fun configureDatabase() {
    val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/chatter_box_db_dev"
    val dbUser = System.getenv("DB_USER") ?: "dev"
    val dbPassword = System.getenv("DB_PASSWORD") ?: "dev"

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(UserTable)
    }
}